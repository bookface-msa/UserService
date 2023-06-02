package com.example.demo.user;

import com.example.demo.Elastic.Elasticuser;
import com.example.demo.Elastic.mqconfig;
import com.example.demo.config.jwtService;
import com.example.demo.firebase.FirebaseInterface;
import com.example.demo.follow.follow;
import com.example.demo.follow.followRepository;
import com.example.demo.follow.followService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;
@Service
//@RequiredArgsConstructor
public class UserService {

        private final UserRepository userRepository;
        private final followRepository followRepositery ;
        private final JdbcTemplate jdbcTemplate;
        private final RabbitTemplate template;
        private final FirebaseInterface IFirebase;
        private final followService followService;
        private final PasswordEncoder passwordEncoder;

        private final jwtService jwtService;




    public UserService(UserRepository userRepository, followRepository followRepositery, JdbcTemplate jdbcTemplate, RabbitTemplate template, FirebaseInterface IFirebase, @Lazy followService followService, PasswordEncoder passwordEncoder,@Lazy jwtService jwtService) {

        this.userRepository = userRepository;
        this.followRepositery = followRepositery;
        this.jdbcTemplate = jdbcTemplate;
        this.template = template;
        this.IFirebase = IFirebase;
        this.followService = followService;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
    }


    public long extractID(HttpServletRequest request){
        String authhead= request.getHeader("Authorization");
        String jwt=authhead.substring(7);//jwt token after "bearer "
        String id=jwtService.extractId(jwt);
        return Long.parseLong(id);
    }
    public User getUserbyid(long id){
        return userRepository.findById(id).orElseThrow(()->new IllegalStateException());
    }

    public User getUserbyemail(String email) throws Exception {
        User u= userRepository.findByEmail(email).get(0);
        if(u!=null){
            return u;
        }
        else{
            throw new Exception("no user with this email found");
        }
    }

    public User getUserbyusername(String username){
        return userRepository.findByUsername(username).orElseThrow(()->new IllegalStateException("getuserbyusernameexception"));
    }

    public User create( userRequest request ) throws Exception {
        User user=new User(request);

        try {
            MultipartFile file = request.getFile();
            if (file != null) {
                String fileName = IFirebase.save(file);
                String imageUrl = IFirebase.getImageUrl(fileName);
                user.setPhotoURL(imageUrl);
            }

        } catch (Exception e) {
            throw new Exception(e);
        }

        User returned = userRepository.save(user);

        System.out.println("Id " + returned.getId());
        Elasticuser userMessage = Elasticuser.builder()
                .id(returned.getId()+"")
                .imageurl(returned.getPhotoURL())
                .bio(returned.getBio())
                .username(returned.getUsername())
                .build();
        template.convertAndSend(mqconfig.EXCHANGE,mqconfig.ROUTING_KEY_CREATE,userMessage);

        return returned;
    }

    public User update(UpdateRequest uuser, HttpServletRequest request) throws Exception{

        User user=userRepository.findById(uuser.getId()).orElseThrow(()->
                new IllegalStateException("excupdate")
        );
        long id2=extractID(request);
        if(user.getId()!=id2){
            throw new Exception("wrong user-update");
        }
        try {
            MultipartFile file = uuser.getFile();
            if (file != null) {
                String fileName = IFirebase.save(file);
                String imageUrl = IFirebase.getImageUrl(fileName);
                user.setPhotoURL(imageUrl);
            }
        } catch (Exception e) {
            throw new Exception(e);
        }

        user.setBio(uuser.getBio());
        user.setEmail(uuser.getEmail());
        user.setFirstname(uuser.getFirstname());
        user.setLastname(uuser.getLastname());
        user.setUsername(uuser.getUsername());
        user.setPassword(passwordEncoder.encode(uuser.getPassword()));

        Elasticuser userMessage = Elasticuser.builder()
                .id(user.getId()+"")
                .imageurl(user.getPhotoURL())
                .bio(user.getBio())
                .username(user.getUsername())
                .build();
        template.convertAndSend(mqconfig.EXCHANGE,mqconfig.ROUTING_KEY_UPDATE,userMessage);

        return userRepository.save(user);
    }

    public void delete(long id , HttpServletRequest request) throws Exception {
        String ids=id+"";

        User u=this.getUserbyid(id);
        if(u!=null){
            long id2=extractID(request);
            if(id!=id2){
                throw new Exception("wrong user-delete");
            }
            if(u.getPhotoURL() != null) {
                String imageUrl = u.getPhotoURL();
                try {
                    String path = new URL(imageUrl).getPath();
                    String fileName = path.substring(path.lastIndexOf('/') + 1);
                    IFirebase.delete(fileName);
                } catch (Exception e) {
                    System.out.println(e);
                }
                handleDeleteUserFollow(id);
                userRepository.delete(getUserbyid(id));
                template.convertAndSend(mqconfig.EXCHANGE,mqconfig.ROUTING_KEY_DELETE,ids);
            }
        }
        else {
            System.out.println("user not found");
        }

    }


    public void incfol(long user1_id , long user2_id){
        User u1 = getUserbyid(user1_id);
        User u2 = getUserbyid(user2_id);
        int new_num_followers = u2.getNum_followers()+1;
        u2.setNum_followers(new_num_followers);
        int new_num_following = u1.getNum_following()+1;
        u1.setNum_following(new_num_following);
        userRepository.save(u1);
        userRepository.save(u2);
    }

    public void decfol(long user1_id , long user2_id){
        User u1 = getUserbyid(user1_id);
        User u2 = getUserbyid(user2_id);
        // check if user 1 & 2 is deleted
        int new_num_followers = u2.getNum_followers()-1;
        u2.setNum_followers(new_num_followers);
        int new_num_following = u1.getNum_following()-1;
        u1.setNum_following(new_num_following);
        userRepository.save(u1);
        userRepository.save(u2);
    }
    public void handleDeleteUserFollow(long user1_id){
       List<follow> following =  followService.getAllFollowers(user1_id); // list of all users that user1 follows
       List<follow>  followers = followService.getAllFollowing(user1_id); // list of all followers of user1 maleesh da3wa b esm elmethod :)
        System.out.println(following);
        System.out.println(followers);

        for(follow f : following){
            followService.unfollow(user1_id, f.getFollowingid());
        }

        for(follow f : followers){
           followService.unfollow(f.getUserid(), user1_id);
        }


    }


}

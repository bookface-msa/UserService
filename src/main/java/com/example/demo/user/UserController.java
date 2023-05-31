package com.example.demo.user;
//
//import com.example.demo.follow.followRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/user")
public class UserController {
@Autowired
private  UserRepository userRepository;
//@Autowired
//private followRepository followRepositery ;

    @GetMapping()
    public List<User> getUsers(){

        return userRepository.findAll();
    }

    @GetMapping("/id")
    public User getUserbyidp(long id){

        return userRepository.findById(id).orElseThrow(()->new IllegalStateException("getuserexecp"));
    }


    public User getUserbyid(long id){
        return userRepository.findById(id).orElseThrow(()->new IllegalStateException("getuserexec"));
    }

    @GetMapping("/email")
    public User getUserbyemail(String email){
        return userRepository.findByEmail(email).get(0);
    }

    @GetMapping("/auth")
    public boolean authenticate(@RequestParam String username,@RequestParam String password){
         User user=userRepository.findByUsername(username).get();
         return user.getPassword().equals(password);
    }
    @PostMapping()
    public User add(@RequestBody User user ){
        user.setNum_following(0);
        user.setNum_followers(0);
        user.setNum_likes(0);
        return userRepository.save(user);
    }

    @DeleteMapping()
    public void delete(@RequestParam long id ){
         userRepository.delete(getUserbyid(id));
    }

    @PutMapping()
    public User update(@RequestBody User uuser){
      User user=userRepository.findById(uuser.getId()).orElseThrow(()->
          new IllegalStateException("excupdate")
      );
        user.setBio(uuser.getBio());
        user.setEmail(uuser.getEmail());
        user.setNum_followers(uuser.getNum_followers());
        user.setNum_following(uuser.getNum_following());
        user.setNum_likes(uuser.getNum_likes());
        user.setFirstname(uuser.getFirstname());
        user.setLastname(uuser.getLastname());
        user.setUsername(uuser.getUsername());
        user.setPassword(uuser.getPassword());
        return userRepository.save(user);
    }


}

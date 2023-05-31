package com.example.demo.follow;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/follow")
@AllArgsConstructor
public class followController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final followRepository followRepository ;
    @GetMapping()
    public List<follow> getallfollows(){
        return followRepository.findAll();
    }

    @PostMapping()
    public follow follow(User user1,User user2 ){
       follow follow=new follow(user1.getId(), user2.getId());
        boolean check=followRepository.existsBymid(follow.getMid());
        System.out.println(check);
        if(check){
            System.out.println("user: "+user1.getId()+  " already follows this user: "+user2.getId());
        }
        else{
            if(user1.getId()==user2.getId()){
                System.out.println("user: "+user1.getId()+  " can not follow himself");
                return null;
            }
            System.out.println("user: "+user1.getId()+  " is now following this user: "+user2.getId());
            return followRepository.insert(follow);
        }
        return null;
    }

    @DeleteMapping()
    public follow unfollow(User user1,User user2 ){
        follow follow=new follow(user1.getId(), user2.getId());
        boolean check=followRepository.existsBymid(follow.getMid());
        System.out.println(check);
        if(check){
            System.out.println("user: "+user1.getId()+  " unfollowed this user: "+user2.getId());
            return followRepository.deleteBymid(follow.getMid()).get();
        }
        else{
            System.out.println("user: "+user1.getId()+  " does not follow this user: "+user2.getId());
        }
        return null;
    }

}

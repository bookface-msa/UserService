package com.example.demo.follow;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.google.firebase.database.annotations.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/follow")
@AllArgsConstructor
public class followController {
    private UserRepository userRepository;
    private final followRepository followRepository ;
    private final followService followService;
    @GetMapping()
    public List<follow> getallfollows(@RequestBody followRequest fr ){
        System.out.println(fr.toString());
        return followService.getAllFollows();
    }

    @PostMapping()
    public follow follow(@RequestBody  followRequest fr ){
        System.out.println(fr.toString());
        return followService.follow(fr);
    }

    @DeleteMapping()
    public follow unfollow(@RequestBody followRequest fr ){
        System.out.println(fr.toString());
        return followService.unfollow(fr);
    }

}

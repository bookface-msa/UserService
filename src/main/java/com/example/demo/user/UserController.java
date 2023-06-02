package com.example.demo.user;
//
//import com.example.demo.follow.followRepository;
import com.example.demo.Elastic.Elasticuser;
import com.example.demo.Elastic.mqconfig;
import com.example.demo.auth.AuthenticationResponse;
import com.example.demo.auth.RegisterRequest;
import com.example.demo.follow.followRepository;
import com.example.demo.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.firebase.FirebaseInterface;

import java.net.URL;
import java.util.List;

@RestController
@RequestMapping(path="api/v1/user")
@RequiredArgsConstructor
public class UserController {
private final UserRepository userRepository;
private final followRepository followRepositery ;
private final UserService userservice;
private final FirebaseInterface IFirebase;
private final RabbitTemplate template;

    @GetMapping()
    public List<User> getUsers(){

        return userRepository.findAll();
    }

    @GetMapping("/id")
    public User getUserbyid(@RequestParam String id){

        return userservice.getUserbyid(Long.parseLong(id));
    }

    @GetMapping("/email")
    public User getUserbyemail(@RequestParam String email) throws Exception {

        return userservice.getUserbyemail(email);
    }

    @GetMapping("/username")
    public User getUserbyusername(@RequestParam String username)  {
        return userservice.getUserbyusername(username);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam long id , HttpServletRequest request) throws Exception {
         userservice.delete(id,request);

    }

    @PutMapping("/update")
    public User update(@ModelAttribute UpdateRequest uuser, HttpServletRequest request) throws Exception{
        return userservice.update(uuser,request);
    }


}

package com.example.demo.auth;


import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

 private final AuthenticationService as;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @ModelAttribute RegisterRequest request
    ) throws Exception {
        System.out.println("HEEEEER \n\n\n");
       return ResponseEntity.ok(as.register(request));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
            ) throws Exception {
        System.out.println(request.toString());
        return ResponseEntity.ok(as.login(request));
    }
}

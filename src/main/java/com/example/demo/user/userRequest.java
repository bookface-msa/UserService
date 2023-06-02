package com.example.demo.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class userRequest {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String bio;
    private MultipartFile file;




}

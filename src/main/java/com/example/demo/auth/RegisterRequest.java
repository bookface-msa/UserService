package com.example.demo.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
   private String firstname;
    private String lastname;
   private String username;
    private String email;
    private String password;

    private String bio;

    private Integer num_followers;

    private Integer num_following;

    private Integer num_likes;
}

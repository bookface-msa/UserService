package com.example.demo.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest {

    private long id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String bio;
    private MultipartFile file;
}

package com.example.demo.Elastic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Elasticuser {
    private String id;
    private String imageurl;
    private String username;
    private String bio;
}

package com.example.demo.follow;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class followService {
    private final followRepository followRepository;

    public List<follow> getAllFollows(){
        return followRepository.findAll();
    }
}

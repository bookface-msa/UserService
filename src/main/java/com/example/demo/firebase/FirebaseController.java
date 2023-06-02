package com.example.demo.firebase;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/picture")
public class FirebaseController {

    @Autowired
    private final FirebaseInterface IFirebase;

    //Checking that I can download images
    @PostMapping("/{fileName}")
    public ResponseEntity download(@PathVariable String fileName) {


        try {
            IFirebase.download(fileName);
        } catch (Exception e) {

        }

        return ResponseEntity.ok().build();
    }

}

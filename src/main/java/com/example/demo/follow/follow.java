package com.example.demo.follow;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@Document
public class follow {
    @Id
    private String id;
    private long userid;
    private long followingid;
    @Indexed(unique = true)
    private String mid;

    public follow(long userid, long followingid) {
        this.userid = userid;
        this.followingid = followingid;
        this.mid=userid+""+followingid+"";
      //  System.out.println( this.mid);
    }
}


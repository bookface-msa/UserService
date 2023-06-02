package com.example.demo.follow;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@Document
@NoArgsConstructor
public class follow {
    @Id
    private String id;
    private long userid; // this
    private long followingid;//follows this
    @Indexed(unique = true)
    private String mid;

    public follow(long userid, long followingid) {
        this.userid = userid;
        this.followingid = followingid;
        this.mid=userid+"_"+followingid+"";
      //  System.out.println( this.mid);
    }

    public follow(followRequest fr) {
        this.userid = Long.parseLong(fr.getUserid());
        this.followingid = Long.parseLong(fr.getFollowingid());
        this.mid=userid+"_"+followingid+"";
        //  System.out.println( this.mid);
    }
}


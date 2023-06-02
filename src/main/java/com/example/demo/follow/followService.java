package com.example.demo.follow;

import com.example.demo.user.User;
import com.example.demo.user.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class followService {
    private final followRepository followRepository;
    private final UserService userService;

    public List<follow> getAllFollows(){
        return followRepository.findAll();
    }

    public follow follow(followRequest fr){
        long user1id= Long.parseLong(fr.getUserid());
       long user2id = Long.parseLong(fr.getFollowingid());
        follow follow=new follow(user1id, user2id);
        boolean check=followRepository.existsBymid(follow.getMid());
        System.out.println(check);
        if(check){
            System.out.println("user: "+user1id+  " already follows this user: "+user2id);
        }
        else{
            if(user1id==user2id){
                System.out.println("user: "+user1id+  " can not follow himself");
                return null;
            }
            System.out.println("user: "+user1id+  " is now following this user: "+user2id);
            userService.incfol(user1id,user2id);
            return followRepository.insert(follow);
        }
        return null;
    }

    public follow unfollow(followRequest fr ){
        long user1id= Long.parseLong(fr.getUserid());
        long user2id = Long.parseLong(fr.getFollowingid());
        follow follow=new follow(user1id, user2id);
        boolean check=followRepository.existsBymid(follow.getMid());
        System.out.println(check);
        if(check){
            System.out.println("user: "+user1id+  " unfollowed this user: "+user2id);
            userService.decfol(user1id,user2id);
            return followRepository.deleteBymid(follow.getMid()).get();
        }
        else{
            System.out.println("user: "+user1id+  " does not follow this user: "+user2id);
        }
        return null;
    }

    public follow unfollow(long user1id,long user2id ){

        follow follow=new follow(user1id, user2id);
        boolean check=followRepository.existsBymid(follow.getMid());
        System.out.println(check);
        if(check){
            System.out.println("user: "+user1id+  " unfollowed this user: "+user2id);
            userService.decfol(user1id,user2id);
            return followRepository.deleteBymid(follow.getMid()).get();
        }
        else{
            System.out.println("user: "+user1id+  " does not follow this user: "+user2id);
        }
        return null;
    }

    public List<follow> getAllFollowers(long userid){
        // returns a list of user that userid follows
        return followRepository.findFollowsByuserid(userid); //find follow where userid=userid (follower)

    }

    public List<follow> getAllFollowing(long userid){
        //returns a list of users that follow userid
        return followRepository.findFollowsByfollowingid(userid); //find follow where followingid=userid
    }


}

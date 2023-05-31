package com.example.demo.follow;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface followRepository extends MongoRepository <follow, String>{

    @Override
    Optional<follow> findById(String s);


    Optional<follow> findFollowByuserid(long id);
    Optional<follow> findFollowByfollowingid(long id);

    Optional<follow> findFollowBymid(String mid);

    Optional<follow> deleteBymid(String mid);


    boolean existsBymid(String s);



    List<follow> findFollowsByuserid(long id);
    List<follow> findFollowsByfollowingid(long id);
}

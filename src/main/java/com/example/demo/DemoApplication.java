package com.example.demo;


import com.example.demo.follow.followController;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import com.example.demo.follow.followRepository;
import com.example.demo.follow.follow;

import java.util.List;
import java.util.Optional;


@SpringBootApplication
//@RestController
public class DemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner(followController fc, followRepository fr, UserRepository ur, MongoTemplate mt){
//		return args -> {
//			Optional<User> u1= ur.findById(0L);
//			Optional<User> u2= ur.findById(4L);
//		//	fc.unfollow(u1.get() ,u2.get());
////			Optional<follow> f2=fr.findById("6475f17201d3024f5fd0861d");
////			List<follow> f3=fr.findFollowsByuserid(0l);
////			System.out.println(f3.get(0).toString());
////			Query q=new Query();
////			q.addCriteria(Criteria.where("userid").is(0l));
////			List<follow> follows=mt.find(q,follow.class);
////			System.out.println(follows.get(0).toString());
//
//		};
//
//	}

	}



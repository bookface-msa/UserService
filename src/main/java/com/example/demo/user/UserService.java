package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> getUsers1(){
        User x=new User("f","l","u","e","p","b");
        return List.of(x);
    }
    public List<User> getUsers2() {
        return jdbcTemplate.query("SELECT * FROM Users", new BeanPropertyRowMapper<>(User.class));
    }

    public void addRecord() {
        String sql = "INSERT INTO Users (firstname, lastname, username,email,password,bio,numFollowers,numFollowing,numLikes)VALUES (\"george\",\"maged\",\"MGMM\",\"email\",\"718xlw\",\"bio\",0,0,0);";
        jdbcTemplate.execute(sql);
    }

}

package com.example.demo.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

    @JsonProperty("firstname")
    @Column(name="firstname", nullable = false)
    private String firstname;
    @JsonProperty("lastname")
    @Column(name="lastname", nullable = false)
    private String lastname;
    @JsonProperty("username")
    @Column(name="username", nullable = false,unique = true)
    private String username;
    @JsonProperty("email")
    @Column(name="email", nullable = false,unique = true)
    private String email;
    @JsonProperty("password")
    @Column(name="password", nullable = false)
    private String password;
    @JsonProperty("bio")
    @Column(name="bio", nullable = false)
    private String bio;
    @JsonProperty("num_followers")
    @Column(name="num_followers", nullable = false)
    private Integer num_followers;
    @JsonProperty("num_following")
    @Column(name="num_following", nullable = false)
    private Integer num_following;
   @JsonProperty("num_likes")
   @Column(name="num_likes", nullable = false)
    private Integer num_likes;

    public User() {

    }

    public User(String firstname, String lastname, String username, String email, String password, String bio) {
        this.id=0L;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.num_followers = 0;
        this.num_following = 0;
        this.num_likes = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getNum_followers() {
        return num_followers;
    }

    public void setNum_followers(Integer num_followers) {
        this.num_followers = num_followers;
    }

    public Integer getNum_following() {
        return num_following;
    }

    public void setNum_following(Integer num_following) {
        this.num_following = num_following;
    }

    public Integer getNum_likes() {
        return num_likes;
    }

    public void setNum_likes(Integer num_likes) {
        this.num_likes = num_likes;
    }

    public User(long id, String firstname, String lastname, String username, String email, String password, String bio) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.num_followers = 0;
        this.num_following = 0;
        this.num_likes = 0;
    }




}

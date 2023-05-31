package com.example.demo.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Data
@Entity
@Table(name="users")
@Builder
public class User  implements UserDetails {


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

    @JsonProperty("role")
    @Column(name="role", nullable = false)
    @Enumerated(EnumType.STRING)
   private Role role;

    public User() {

    }

    public User(String firstname, String lastname, String username, String email, String password, String bio) {
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

    public User(Long id, String firstname, String lastname, String username, String email, String password, String bio, Integer num_followers, Integer num_following, Integer num_likes, Role role) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.num_followers = num_followers;
        this.num_following = num_following;
        this.num_likes = num_likes;
        this.role = role;
    }
//    public User(Optional<User> u) {
//        this.id = u.get().getId();
//        this.firstname = u.get().getFirstname();
//        this.lastname = u.get().getLastname()lastname;
//        this.username = username;
//        this.email = email;
//        this.password = password;
//        this.bio = bio;
//        this.num_followers = 0;
//        this.num_following = 0;
//        this.num_likes = 0;
//    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
    }

    @Override
    public String getPassword(){
        return password;
    }

}

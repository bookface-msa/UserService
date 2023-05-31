package com.example.demo.auth;

import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.config.jwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
 private final UserRepository ur;
    private final PasswordEncoder pe;
    private final jwtService js;
    private final AuthenticationManager am;
    public AuthenticationResponse register(RegisterRequest request){
    var user=User.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .bio(request.getBio())
            .num_followers(0)
            .num_following(0)
            .num_likes(0)
            .role(Role.USER)
            .username(request.getUsername())
            .password(pe.encode(request.getPassword()))
            .build();
    ur.save(user);
    var jwtToken=js.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse login(AuthenticationRequest request){
  am.authenticate(new UsernamePasswordAuthenticationToken(
          request.getUsername(),request.getPassword()
  ));
  var user=ur.findByUsername(request.getUsername()).orElseThrow(()->{
      throw new UsernameNotFoundException("user not found");
  });
 var jwtToken=js.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();


    }
}

package com.example.demo.auth;

import com.example.demo.Elastic.Elasticuser;
import com.example.demo.Elastic.mqconfig;
import com.example.demo.user.*;
import com.example.demo.config.jwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
 private final UserRepository ur;
    private final UserController uc;
    private final UserService us;
    private final PasswordEncoder pe;
    private final jwtService js;
    private final AuthenticationManager am;

    private final RabbitTemplate template;
    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        System.out.println(request);
        var ure= userRequest.builder().
                file(request.getFile())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .bio(request.getBio())
                .username(request.getUsername())
                .password(pe.encode(request.getPassword()))
                .build();
    var user=us.create(ure);
    var jwtToken=js.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).id(user.getId()+"").build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) throws Exception {
        System.out.println(1);
        try{
  am.authenticate(new UsernamePasswordAuthenticationToken(
          request.getUsername(),request.getPassword()
  ));}
        catch (Exception e){
            System.out.println(e);
            throw new Exception(e);
        }
        try {
           User user=ur.findByUsername(request.getUsername()).get();
           System.out.println(user.toString());
            var jwtToken=js.generateToken(user);
            return AuthenticationResponse.builder().token(jwtToken).id(user.getId()+"").build();
      } catch (Exception e) {
            System.out.println(e);
            return AuthenticationResponse.builder().token("mafesh token").build();
             //   throw new Exception("user not found");
      }





    }
}

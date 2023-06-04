package com.example.demo.config;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    //binds jwt to the rest of the application
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
         http.csrf(csrf -> csrf.disable())
                 .cors(cors->cors.disable())
                 .authorizeHttpRequests((authz) -> authz
                         .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                         .requestMatchers("/user/api/v1/auth/register","/user/api/v1/auth/login","/actuator/prometheus")//white list (register,login)
                         .permitAll()
                         .anyRequest()//all other requests
                         .authenticated()

                 )
                 .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .authenticationProvider(authenticationProvider)
                 .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//                .httpBasic(withDefaults());

        return http.build();
        //edited due to deprecation
  }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/api/v1/auth/register","/api/v1/auth/login");//white list (register,login);
//    }

//    {
//        "username":"Shaalan800",
//            "password":"pass0800",
//            "firstname":"Mostafa",
//            "lastname":"Shaalan",
//            "bio":"Lmaza na7n hona",
//            "email":"Ay7aga"
//    }

}

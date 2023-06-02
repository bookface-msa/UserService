package com.example.demo.config;


import com.example.demo.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    //@Autowired
    private final jwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal
            (
                    @NonNull HttpServletRequest request,
                    @NonNull HttpServletResponse response,
                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader=request.getHeader("Authorization");
        final String jwt;
        final String username;
        if(authHeader == null || !(authHeader.startsWith("Bearer "))){
            filterChain.doFilter(request,response);//next in line
            return;
        }
        jwt=authHeader.substring(7);//jwt token after "bearer "
        username=jwtService.extractId(jwt);//toto extract username from jwt token;
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
           // username in token and has not been authenticated before
            System.out.println(username);
          String username2= userRepository.findById(Long.parseLong(username)).get().getUsername();
            UserDetails u=this.userDetailsService.loadUserByUsername(username2);
            System.out.println(username2);
            if(jwtService.isTokenValid(jwt,u)){
                UsernamePasswordAuthenticationToken authtoken=new UsernamePasswordAuthenticationToken(
                        u,null,u.getAuthorities()
                );

                authtoken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authtoken);
            }
            else {
                System.out.println("token not valid");
            }
        }
        filterChain.doFilter(request,response);

    }
}

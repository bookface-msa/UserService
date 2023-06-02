package com.example.demo.config;


import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class jwtService {

    private final UserRepository userRepository;
  private static final String key="50645367566B5970337336763979244226452948404D6351665468576D5A7134";
    public String extractId(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public<T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims c=extractAllClaim(token);
        return claimResolver.apply(c);
        //extract a single claim
        //general
    }

    private Claims extractAllClaim(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())//decoder
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
     byte[] keyByte= Decoders.BASE64.decode(key);
     return Keys.hmacShaKeyFor(keyByte);
    }
//token methods
    //generate
    public String generateToken(Map<String,Object> extraClaims,
                              User ud){
         String id=ud.getId()+"";
         System.out.println("ID: "+id);
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();//24 hours +1000ms
    }

    public String generateToken(User ud) {
        return generateToken(new HashMap<String, Object>(), ud);
    }

    //validate token

    boolean isTokenValid(String token,UserDetails ud){
        final String username=extractId(token);
        String username2= userRepository.findById(Long.parseLong(username)).get().getUsername();
        return (username2.equals(ud.getUsername()))&& (!isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
}

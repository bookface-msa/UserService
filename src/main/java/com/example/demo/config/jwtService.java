package com.example.demo.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class jwtService {
  private static final String key="50645367566B5970337336763979244226452948404D6351665468576D5A7134";
    public String extractUsername(String token){
        String username="";

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
                              UserDetails ud){

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(ud.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();//24 hours +1000ms
    }

    public String generateToken(UserDetails ud) {
        return generateToken(new HashMap<String, Object>(), ud);
    }

    //validate token

    boolean isTokenValid(String token,UserDetails ud){
        final String username=extractUsername(token);
        return (username.equals(ud.getUsername()))&& (!isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
}

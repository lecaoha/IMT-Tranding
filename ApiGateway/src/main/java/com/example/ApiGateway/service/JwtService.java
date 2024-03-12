package com.example.ApiGateway.service;

import com.example.ApiGateway.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private static final String SECRET_KEY = "88733625B12489C443DA5D9D3A771278FCBCBD1F9C0F73957EFD44B46C18A79BA515BE94EF53F729BAFD397B33CC0A172E758C3A7F501BA33A0F47E67AD9C2EDB5C409A1145FF5499AE797A3225D6F8B7E19AE7D111BB6ED9850B1DD1DD9592E721FBDD143D143EF32576C6B19965FE2C0406FBC4E5BA58841B76F3CAEEF73D1";

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject) ;
    }
    public String generateToken(User userDetails){
        return generateToken(new HashMap<>(),  userDetails);
    }
    public String generateToken(Map<String, Object> extractClaims, User userDetails){
        return Jwts.builder().setClaims(extractClaims)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24 ))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public boolean isTokenValid(String token, User userDetails){
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getEmail()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSignInKey()).build()
                .parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }
}

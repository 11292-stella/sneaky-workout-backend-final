package com.example.Prova_Progetto_Personal_Trainer.security;



import com.example.Prova_Progetto_Personal_Trainer.exception.NotFoundException;
import com.example.Prova_Progetto_Personal_Trainer.model.User;
import com.example.Prova_Progetto_Personal_Trainer.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class JwtTool {
    @Value("${jwt.duration}")
    private long durata;

    @Value("${jwt.secret}")
    private String chiaveSegreta;

    @Autowired
    private UserService userService;

    public String createToken(User user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("roles", List.of(user.getRole().name()));


        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + durata))
                .subject(String.valueOf(user.getId()))
                .claims(claims) // <-- Aggiungi i claims qui!
                .signWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .compact();
    }

    public void validateToken(String token) {
        Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .build()
                .parse(token);
    }

    public User getUserFromToken(String token) throws NotFoundException {
        // Estrai i claims dal token
        io.jsonwebtoken.Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String subject = claims.getSubject();

        if (subject == null || !subject.matches("\\d+")) {
            throw new NotFoundException("Invalid user ID in token.");
        }

        int id = Integer.parseInt(subject);



        return userService.getUser(id);
    }
}

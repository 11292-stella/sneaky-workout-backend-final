package com.example.Prova_Progetto_Personal_Trainer.security;



import com.example.Prova_Progetto_Personal_Trainer.exception.NotFoundException;
import com.example.Prova_Progetto_Personal_Trainer.exception.UnAuthorizedException;
import com.example.Prova_Progetto_Personal_Trainer.model.User;
import com.example.Prova_Progetto_Personal_Trainer.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class JwtTool {
    @Value("${jwt.duration}")
    private long durata;

    @Value("${jwt.secret}")
    private String chiaveSegreta;

    @Autowired
    private UserService userService; // Potrebbe non essere più necessario per getUserFromToken se estrai tutto dal token

    public String createToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        // Aggiungiamo il ruolo dell'utente come claim nel token
        claims.put("roles", List.of(user.getRole().name())); // user.getRole() è il tuo enum Role

        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + durata))
                .subject(String.valueOf(user.getId()))
                .claims(claims)
                .signWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .compact();
    }

    public void validateToken(String token) {
        Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .build()
                .parse(token);
    }

    // MODIFICA QUI: Questo metodo ora restituisce un oggetto Authentication
    // che include l'utente e le sue autorità estratte direttamente dal token.
    public Authentication getAuthentication(String token) throws NotFoundException, UnAuthorizedException {
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String subject = claims.getSubject();

        if (subject == null || !subject.matches("\\d+")) {
            throw new NotFoundException("Invalid user ID in token.");
        }

        // Estrai i ruoli dal token
        List<String> roles = (List<String>) claims.get("roles");
        if (roles == null) {
            // Se i ruoli non sono presenti nel token, potresti voler gestire questo come un errore
            // o recuperare l'utente dal DB per ottenere i ruoli.
            // Per ora, lo trattiamo come un errore se i ruoli sono attesi nel token.
            throw new UnAuthorizedException("Roles not found in token.");
        }

        // Crea le GrantedAuthority dagli stringa dei ruoli
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // Recupera l'utente dal database (opzionale, ma utile per avere l'oggetto completo)
        // Potresti voler passare solo l'ID utente qui se non hai bisogno dell'oggetto User completo
        // in SecurityContextHolder, ma per coerenza con il tuo JwtFilter attuale, lo recuperiamo.
        User user = userService.getUser(Integer.parseInt(subject));

        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }
}

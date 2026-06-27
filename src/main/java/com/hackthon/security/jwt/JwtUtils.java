package com.hackthon.security.jwt;

import com.hackthon.enums.RoleName;
import com.hackthon.modele.CompteUtilisateur;
import com.hackthon.repository.CompteUtilisateurRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Component
public class JwtUtils {
    long JWT_VALIDITY = 6L * 30 * 24 * 60 * 60;

    private static final String AUTHORITIES_KEY = "roles";

    private final Key key;

    private final JwtParser jwtParser;
    private final CompteUtilisateurRepository compteUtilisateurRepository;

    public JwtUtils(@Value("${jwt.secret}") String secret, CompteUtilisateurRepository compteUtilisateurRepository) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.compteUtilisateurRepository = compteUtilisateurRepository;
        this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();

    }

    public String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
//
//
//        // 1. Récupérer les rôles à partir de l'authentification
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//
//        // 2. Transformer les rôles en liste de String (["ADMIN", "UTILISATEUR", etc.])
//        List<String> roles = authorities.stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
        CompteUtilisateur compteUtilisateur= compteUtilisateurRepository.findByEmail(authentication.getName()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "utilisateur non trouvé"));

        RoleName role=compteUtilisateur.getRole();

        // 3. Ajouter les rôles dans les claims
        claims.put("roles", role.name());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(authentication.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_VALIDITY * 1000))
                .signWith(key).compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        String role = claims.get(AUTHORITIES_KEY).toString();
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}

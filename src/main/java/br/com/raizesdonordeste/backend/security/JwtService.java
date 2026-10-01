package br.com.raizesdonordeste.backend.security;

import br.com.raizesdonordeste.backend.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtService {

    private final String secretKey = "7GsD8Feo0PLUokJF2gZENetLFREDd1RUlQi3y4r+POI=";
    private final long expiration = 3600000L; // 1 hora

    public String extrairSubject(String token) {
        SecretKey key = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secretKey)
        );

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String extrairRole(String token) {
        SecretKey key = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secretKey)
        );

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    public String gerarToken(Usuario usuario) {
        Date agora = new Date();
        Date expiracaoToken = new Date(agora.getTime() + expiration);

        SecretKey key = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secretKey)
        );

        return Jwts.builder()
                .subject(usuario.getId().toString())
                .claim("email", usuario.getEmail())
                .claim("role", usuario.getRole().name())
                .issuedAt(agora)
                .expiration(expiracaoToken)
                .signWith(key)
                .compact();
    }
}

package com.sigeev.usuarios.infrastructure.security;

import com.sigeev.usuarios.domain.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String gerarToken(Usuario usuario) {
        return gerarToken(new HashMap<>(), usuario);
    }

    public String gerarToken(Map<String, Object> extraClaims, Usuario usuario) {
        return buildToken(extraClaims, usuario, jwtExpiration);
    }

    public String gerarRefreshToken(Usuario usuario) {
        return buildToken(new HashMap<>(), usuario, refreshExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, Usuario usuario, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(usuario.getEmail())
                .claim("usuarioId", usuario.getUsuarioId().toString())
                .claim("perfil", usuario.getPerfil().name())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extrairUsername(String token) {
        return extrairTodosClaims(token).getSubject();
    }

    public String renovarToken(String token) {
        final Claims claims = extrairTodosClaims(token);
        return buildToken(new HashMap<>(), extractUsuario(claims), jwtExpiration);
    }

    private Usuario extractUsuario(Claims claims) {
        return Usuario.builder()
                .email(claims.getSubject())
                .build();
    }

    private Claims extrairTodosClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public boolean isTokenValido(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

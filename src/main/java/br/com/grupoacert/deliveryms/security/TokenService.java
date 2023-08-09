package br.com.grupoacert.deliveryms.security;

import br.com.grupoacert.deliveryms.domain.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private String expiration;
    private static final String CARGOS = "cargos";
    private static final String TOKEN_PREFIX = "Bearer ";


    public String getToken(Usuario usuario) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + Long.valueOf(expiration));

        List<String> listaCargos = Collections.singletonList(usuario.getCargo().getNomeCargo());

        String token = Jwts.builder()
                .setIssuer("delivery")
                .claim(Claims.ID, usuario.getId())
                .claim(CARGOS, listaCargos)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return TOKEN_PREFIX + token;
    }

    public UsernamePasswordAuthenticationToken isValid(String token) {
        if (token == null) {
            return null;
        }

        Claims body = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        Integer idUsuario = body.get(Claims.ID, Integer.class);

        if (idUsuario != null) {
            List<String> cargos = body.get(CARGOS, List.class);

            List<SimpleGrantedAuthority> rolesGrantedAuthority = cargos.stream()
                    .map(cargo -> new SimpleGrantedAuthority(cargo))
                    .collect(Collectors.toList());

            return new UsernamePasswordAuthenticationToken(idUsuario, null, rolesGrantedAuthority);
        }
        return null;
    }

}

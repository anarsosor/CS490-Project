package HealthCare.service;

import HealthCare.dto.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    public static final String SECRET = "ThisIsSecretKeyWithHS256AndMustHaveASizeMoreThan256bits";

    public Map<String, String> generateToken(UserDTO userDTO) {
        Map<String, Object> claims = new HashMap<>();
        if (userDTO.isDoctor()) {
            claims.put("isDoctor", true);
        }
        if (userDTO.isPatient()) {
            claims.put("isPatient", true);
        }
        if (userDTO.isPharmacist()) {
            claims.put("isPharmacist", true);
        }
        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .setId(Long.toString(userDTO.getId()))
                .setSubject(userDTO.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 72)) // 72 hours
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

        Map<String, String> jwtTokenGen = new HashMap<>();
        jwtTokenGen.put("token", jwtToken);
        jwtTokenGen.put("message", Long.toString(userDTO.getId()));
        return jwtTokenGen;
    }

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

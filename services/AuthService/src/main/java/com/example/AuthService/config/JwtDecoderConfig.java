package com.example.AuthService.config;



import com.example.AuthService.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtDecoderConfig implements JwtDecoder {

    private final JwtService jwtService;
    private NimbusJwtDecoder nimbusJwtDecoder = null;


    @Value("${jwt.secret}")
    private  String secretKey;

    @Override
    public Jwt decode(String token) throws JwtException {

        try {
            if(!jwtService.verifyToken(token)) {
                throw new JwtException("Invalid JWT token");
            }
            if(Objects.isNull(nimbusJwtDecoder)) {

                SecretKey key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HS512");
                nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(key)
                        .macAlgorithm(MacAlgorithm.HS512)
                        .build();
            }
        } catch (Exception e) {
            throw new JwtException("Invalid JWT token: " + e.getMessage());
        }

        return nimbusJwtDecoder.decode(token);
    }
}

package com.example.AuthService.service;

import com.example.AuthService.entity.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;


@Service
public class JwtService {

    @Value("${jwt.secret}")
    private  String secretKey;

    public String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        Date issueTime = new Date();
        Date expirationTime = new Date(issueTime.getTime() + 3600000); // 1 hour validity

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issueTime(issueTime)
                .expirationTime(expirationTime)
                .claim("role", user.getRole())
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());


        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(secretKey));
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        return jwsObject.serialize();
    }



    public String generateRefreshToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        Date issueTime = new Date();
        Date expirationTime = new Date(issueTime.getTime() + 7000000); // 1 hour validity

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issueTime(issueTime)
                .expirationTime(expirationTime)
                .claim("role", user.getRole())
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());


        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(secretKey));
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        return jwsObject.serialize();
    }


    public boolean verifyToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        if(expirationTime.before(new Date())){
            return false;
        }
         return signedJWT.verify(new MACVerifier(secretKey));
    }
}

package com.rr.dreamtracker.service;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.rr.dreamtracker.exception.InvalidJWTTokenException;
import com.rr.dreamtracker.security.RSAProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTService {

    @Value("${jwt.expire-length:3600000}")
    private long validityInMilliseconds;

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;
    private final Algorithm algorithm;
    private final RSAProperties rsaProperties;

    private final UserService userDetail;

    public JWTService(RSAProperties rsaProperties, UserService userDetail) {
        this.rsaProperties = rsaProperties;
        this.userDetail = userDetail;

        loadKeys();

        this.algorithm = Algorithm.RSA256(publicKey, privateKey);
    }

    public String createToken(String username, String roles) {
        try {
            return com.auth0.jwt.JWT.create()
                    .withIssuer("auth0")
                    .withSubject(username)
                    .withClaim("role", roles)
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + validityInMilliseconds))
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String extractUsername(String token) {
        var decoded = decodeJWT(token);
        if (decoded == null) return null;

        return decoded.getSubject();
    }

    public Date extractExpiration(String token) {
        var decoded = decodeJWT(token);
        if (decoded == null) return null;
        return decoded.getExpiresAt();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        DecodedJWT decodedJWT = decodeJWT(token);
        if (decodedJWT == null) return false;
        String username = decodedJWT.getSubject();
        boolean isExpired = decodedJWT.getExpiresAt().before(new Date());
        return (username.equals(userDetails.getUsername()) && !isExpired);
    }

    public boolean validateToken(String token) {
        DecodedJWT decodedJWT = decodeJWT(token);
        if (decodedJWT == null) return false;
        UserDetails userDetails = userDetail.loadUserByUsername(decodedJWT.getSubject());
        String username = decodedJWT.getSubject();
        boolean isExpired = decodedJWT.getExpiresAt().before(new Date());
        return (username.equals(userDetails.getUsername()) && !isExpired);
    }

    public boolean isTokenExpired(String token) {
        DecodedJWT decodedJWT = decodeJWT(token);
        if (decodedJWT == null) return false;
        return decodedJWT.getExpiresAt()
                .before(new Date());
    }

    private DecodedJWT decodeJWT(String token) {
        DecodedJWT decodedJWT;
        try {
            JWTVerifier verifier = com.auth0.jwt.JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();
            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new InvalidJWTTokenException("Token is invalid!");
        }
        return decodedJWT;
    }

    private void loadKeys() {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
                    Base64.getDecoder().decode(rsaProperties.getPrivateKey()));
            privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
                    Base64.getDecoder().decode(rsaProperties.getPublicKey()));
            publicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException();
        }
    }
}

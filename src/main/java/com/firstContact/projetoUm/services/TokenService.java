package com.firstContact.projetoUm.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.firstContact.projetoUm.entity.User;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;

import java.time.Instant;

@Service
public class TokenService {

    private String secret = "${tokenSecret}";

    public String generateToken(User user) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(Instant.now().plus(2, ChronoUnit.HOURS))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar Token JWT", exception);
        }
    }
    public String validadeToken( String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(token);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

}

package br.com.hungry.domain.services;

import br.com.hungry.app.dtos.Credencial;
import br.com.hungry.app.dtos.Token;
import br.com.hungry.infra.db.models.CentroDistribuicao;
import br.com.hungry.infra.db.repositories.CentroDistribuicaoRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    @Autowired
    CentroDistribuicaoRepository repository;

    @Value("${jwt.secret}")
    String secret;

    public Token generateToken(@Valid Credencial credencial, String role) {
        Algorithm alg = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withSubject(credencial.email())
                .withIssuer("Hungry")
                .withExpiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
                .sign(alg);
        return new Token(token, "JWT", "Bearer", role);
    }

    public CentroDistribuicao getValidateUser(String token) {
        Algorithm alg = Algorithm.HMAC256(secret);
        var email = JWT.require(alg)
                .withIssuer("Hungry")
                .build()
                .verify(token)
                .getSubject();

        return repository.findByEmail(email)
                .orElseThrow(() -> new JWTVerificationException("Usuário inválido"));
    }


}
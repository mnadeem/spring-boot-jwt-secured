package com.github.nadeem.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.nadeem.support.ActiveUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class DefaultTokenService implements TokenService {

	private static final String CLAIM_ROLES = "roles";
	private static final String CLAIM_EMAIL = "email";

	@Value("${jwt.secret}")
    private String jwtSecret;

	@Override
	@SuppressWarnings("unchecked")
	public ActiveUser getActiveUser(String token) {
		Claims body = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return new ActiveUser((String) body.get(CLAIM_EMAIL), ((List<String>) body.get(CLAIM_ROLES)));
	}

	@Override
	public String getToken(ActiveUser activeUser) {
		String token = Jwts
				.builder()
				.setId("appJWT")
				.setSubject(activeUser.getId())
				.claim(CLAIM_ROLES, activeUser.getRoles())
				.claim(CLAIM_EMAIL, activeUser.getId())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 9000))
				.signWith(SignatureAlgorithm.HS512,
						jwtSecret.getBytes()).compact();
		return token;
	}
}

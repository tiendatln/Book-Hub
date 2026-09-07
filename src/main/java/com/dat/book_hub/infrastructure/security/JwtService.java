package com.dat.book_hub.infrastructure.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long jwtExpiration;

	@Value("${jwt.refreshExpiration}")
	private long refreshExpiration;

	@Async("taskExecutor")
	public String extractUsername(String token) {
		return extractClaim(token, claims -> claims.getSubject());
	}

	@Async("taskExecutor")
	public String extractTokenType(String token) {
		return extractClaim(token, claims -> claims.get("token_type", String.class));
	}

	@Async("taskExecutor")
	public String extractRole(String token) {
		return extractClaim(token, claims -> claims.get("role", String.class));
	}

	@Async("taskExecutor")
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	@Async("taskExecutor")
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return generateToken(extraClaims, userDetails, jwtExpiration, "access");
	}

	@Async("taskExecutor")
	public String generateRefreshToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails, refreshExpiration, "refresh");
	}

	@Async("taskExecutor")
	public boolean isRefreshTokenValid(String token, UserDetails userDetails) {
		String tokenType = extractClaim(token, claims -> claims.get("token_type", String.class));
		return "refresh".equals(tokenType) && isTokenValid(token, userDetails.getUsername(), tokenType);
	}

	@Async("taskExecutor")
	private String generateToken(
			Map<String, Object> extraClaims,
			UserDetails userDetails,
			long expiration,
			String tokenType) {
		Date issuedAt = new Date();
		String role = userDetails.getAuthorities().stream()
				.map(authority -> authority.getAuthority())
				.findFirst()
				.orElse("");
		return Jwts.builder()
				.claims(extraClaims)
				.claim("token_type", tokenType)
				.claim("role", role)
				.subject(userDetails.getUsername())
				.issuedAt(issuedAt)
				.expiration(new Date(issuedAt.getTime() + expiration))
				.signWith(getSigningKey())
				.compact();
	}

	@Async("taskExecutor")
	public Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

	@Async("taskExecutor")
	public boolean isTokenValid(
            String token,
            String username,
            String expectedTokenType) {

        try {

            Claims claims = extractAllClaims(token);

            String tokenUsername = claims.getSubject();

            String tokenType =
                    claims.get("tokenType", String.class);

            Date expiration =
                    claims.getExpiration();

            return tokenUsername.equals(username)
                    && tokenType.equals(expectedTokenType)
                    && expiration.after(new Date());

        } catch (Exception e) {

            return false;
        }
    }

	@Async("taskExecutor")
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	@Async("taskExecutor")
	private Date extractExpiration(String token) {
		return extractClaim(token, claims -> claims.getExpiration());
	}

	@Async("taskExecutor")
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
		return claimsResolver.apply(claims);
	}

	@Async("taskExecutor")
	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}
}
package com.leets.team2.xclone.utils.jwt;

import com.leets.team2.xclone.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

  private final String accessValidity;
  private final String refreshValidity;

  private final SecretKey accessKey;
  private final SecretKey refreshKey;

  public JwtUtils(
      @Value("${jwt.access.secret}") String accessSecret,
      @Value("${jwt.access.validity}") String accessValidity,
      @Value("${jwt.refresh.secret}") String refreshSecret,
      @Value("${jwt.refresh.validity}") String refreshValidity
  ) {
    this.refreshKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(refreshSecret.getBytes()));
    this.accessKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(accessSecret.getBytes()));

    this.accessValidity = accessValidity;
    this.refreshValidity = refreshValidity;
  }

  public String generateAccessToken(String memberTag) {
    return Jwts.builder()
        .subject(memberTag)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + Long.parseLong(accessValidity)))
        .signWith(this.accessKey, Jwts.SIG.HS256)
        .compact();
  }

  public void verifyAccessToken(String token) {
    try {
      Claims claims = Jwts.parser()
                      .verifyWith(accessKey)
                      .build()
                      .parseSignedClaims(token)
                      .getPayload();

      if (claims.getExpiration().before(new Date(System.currentTimeMillis()))) {
        throw new ExpiredJwtException(null, claims, "만료된 토큰입니다.");
      }
    } catch (SignatureException e) {
      throw new InvalidTokenException();
    }
  }

  public String generateRefreshToken(String memberTag) {
    return Jwts.builder()
        .subject(memberTag)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(
            System.currentTimeMillis() + Long.parseLong(refreshValidity)
        ))
        .signWith(refreshKey, Jwts.SIG.HS256)
        .compact();
  }

  public void verifyRefreshToken(String token) {
    try {
      Claims claims = Jwts.parser()
                          .verifyWith(refreshKey)
                          .build()
                          .parseSignedClaims(token)
                          .getPayload();

      if (claims.getExpiration().before(new Date(System.currentTimeMillis()))) {
        throw new ExpiredJwtException(null, claims, "만료된 토큰입니다.");
      }
    } catch (SignatureException e) {
      throw new InvalidTokenException();
    }
  }

  public Claims parseAccessTokenPayloads(String accessToken) {
    return Jwts.parser()
        .verifyWith(accessKey)
        .build()
        .parseSignedClaims(accessToken)
        .getPayload();
  }

  public Claims parseRefreshTokenPayloads(String refreshToken) {
    return Jwts.parser()
        .verifyWith(refreshKey)
        .build()
        .parseSignedClaims(refreshToken)
        .getPayload();
  }
}

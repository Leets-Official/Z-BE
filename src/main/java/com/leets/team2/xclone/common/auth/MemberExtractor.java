package com.leets.team2.xclone.common.auth;

import static com.leets.team2.xclone.utils.cookie.CookieSettings.ACCESS_TOKEN;

import com.leets.team2.xclone.domain.member.entities.Member;
import com.leets.team2.xclone.domain.member.service.MemberService;
import com.leets.team2.xclone.exception.ExpiredTokenException;
import com.leets.team2.xclone.exception.InvalidTokenException;
import com.leets.team2.xclone.exception.NotFoundAccessTokenException;
import com.leets.team2.xclone.utils.jwt.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor
public class MemberExtractor {

  private final JwtUtils jwtUtils;
  private final MemberService memberService;

  public void extractMemberFromToken() {
    HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

    Optional<String> extractedAccessTokenFromCookie = extractTokenFromCookie(httpServletRequest,
        ACCESS_TOKEN.getName());
    if (extractedAccessTokenFromCookie.isEmpty()) {
      throw new NotFoundAccessTokenException();
    }

    String accessToken = extractedAccessTokenFromCookie.get();
    Member member;
    try {
      this.jwtUtils.verifyAccessToken(accessToken);
      String memberTag = this.jwtUtils.parseAccessTokenPayloads(accessToken).getSubject();
      member = this.memberService.findMemberByTag(memberTag);
    } catch (InvalidTokenException invalidTokenException) {
      throw new InvalidTokenException();
    } catch (ExpiredJwtException expiredJwtException) {
      throw new ExpiredTokenException();
    }

    MemberContext.setMember(member);
  }

  private Optional<String> extractTokenFromCookie(HttpServletRequest httpServletRequest,
      String cookieName) {
    Cookie[] cookies = httpServletRequest.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(cookieName)) {
          return Optional.of(cookie.getValue());
        }
      }
    }
    return Optional.empty();
  }
}

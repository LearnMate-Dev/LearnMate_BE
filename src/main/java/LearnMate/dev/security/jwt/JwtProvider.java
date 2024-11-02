package LearnMate.dev.security.jwt;

import LearnMate.dev.common.ApiException;
import LearnMate.dev.common.ErrorStatus;
import LearnMate.dev.model.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key;
    private final Long ACCESS_TOKEN_EXPIRE_TIME;
    private final Long REFRESH_TOKEN_EXPIRE_TIME;

    // JWT Provider 생성자
    public JwtProvider(@Value("${jwt.secret_key}") String secretKey,
                       @Value("${jwt.access_token_expire}") Long accessTokenExpire,
                       @Value("${jwt.refresh_token_expire}") Long refreshTokenExpire) {

        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);  // 안전한 256비트 비밀 키 생성
        this.ACCESS_TOKEN_EXPIRE_TIME = accessTokenExpire;
        this.REFRESH_TOKEN_EXPIRE_TIME = refreshTokenExpire;
    }

    // 세션에 AccessToken과 RefreshToken을 저장하는 메서드
    public void setSessionTokens(User user, HttpSession session) {
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);

        session.setAttribute("accessToken", accessToken);
        session.setAttribute("refreshToken", refreshToken);
    }

    /**
     * AccessToken 생성 메서드
     */
    public String generateAccessToken(User user) {
        Date expiredAt = new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .claim("user_id", user.getId())  // User의 ID를 포함
                .claim("user_name", user.getName())  // User의 이름 포함
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * RefreshToken 생성 메서드
     */
    public String generateRefreshToken(User user) {
        Date expiredAt = new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .claim("user_id", user.getId())  // User의 ID를 포함
                .claim("user_name", user.getName())  // User의 이름 포함
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 세션에서 AccessToken을 가져오는 메서드
    public String getAccessTokenFromSession(HttpSession session) {
        return (String) session.getAttribute("accessToken");
    }

    // 세션에서 RefreshToken을 가져오는 메서드
    public String getRefreshTokenFromSession(HttpSession session) {
        return (String) session.getAttribute("refreshToken");
    }

    /**
     * JWT 토큰에서 User ID 추출
     */
    public Long getUserId(String token) {
        return parseClaims(token).get("user_id", Long.class);
    }

    /**
     * JWT 토큰에서 Claims(토큰 정보)를 파싱
     */
    public Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new ApiException(ErrorStatus._JWT_EXPIRED);
        }
    }

    /**
     * JWT 토큰 유효성 검증 (세션 기반)
     */
    public String validateToken(HttpSession session) {
        String token = getAccessTokenFromSession(session);
        if (token == null) {
            return "NOT_FOUND";
        }
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return "VALID";
        } catch (ExpiredJwtException e) {
            return "EXPIRED";
        } catch (SignatureException | MalformedJwtException e) {
            return "INVALID";
        }
    }

    /**
     * JWT 토큰을 기반으로 Authentication 객체 생성 (User 정보 포함)
     */
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);

        Long userId = claims.get("user_id", Long.class);
        String userName = claims.get("user_name", String.class);  // 클레임 키를 "user_name"으로 수정

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(userName, "", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    /**
     * SecurityContext에 인증 객체 설정
     */
    private void setContextHolder(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * JWT 토큰의 만료 시간을 가져옴
     */
    public Long getExpiration(String token) {
        Date expiration = Jwts.parserBuilder().setSigningKey(key)
                .build().parseClaimsJws(token).getBody().getExpiration();

        long now = new Date().getTime();
        return expiration.getTime() - now;
    }

}
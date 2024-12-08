package LearnMate.dev.security.jwt;

import LearnMate.dev.common.status.ErrorStatus;
import LearnMate.dev.common.exception.ApiException;
import LearnMate.dev.model.entity.User;
import LearnMate.dev.security.security.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key;
    private final Long ACCESS_TOKEN_EXPIRE_TIME;
    private final Long REFRESH_TOKEN_EXPIRE_TIME;

    public JwtProvider(@Value("${jwt.secret_key}") String secretKey,
                       @Value("${jwt.access_token_expire}") Long accessTokenExpire,
                       @Value("${jwt.refresh_token_expire}") Long refreshTokenExpire) {

        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
        this.ACCESS_TOKEN_EXPIRE_TIME = accessTokenExpire;
        this.REFRESH_TOKEN_EXPIRE_TIME = refreshTokenExpire;
    }

    // AccessToken 생성
    public String generateAccessToken(User user) {
        Date expiredAt = new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME);
        return Jwts.builder()
                .claim("user_id", user.getId())
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // RefreshToken 생성
    public String generateRefreshToken(User user) {
        Date expiredAt = new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME);
        return Jwts.builder()
                .claim("user_id", user.getId())
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public void storeRefreshTokenInSession(User user, HttpSession session) {
        String refreshToken = generateRefreshToken(user);
        session.setAttribute("refreshToken", refreshToken);
    }

    // Token 검증
    public String validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return "VALID";
        } catch (ExpiredJwtException e) {
            return "EXPIRED";
        } catch (SignatureException | MalformedJwtException e) {
            return "INVALID";
        }
    }

    // Token에서 Claims 추출
    public Claims getClaimsFromToken(String token) {
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

    // Token에서 User ID 추출
    public Long getUserId(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("user_id", Long.class);
    }

    // AccessToken 갱신
    public Authentication refreshAccessToken(String refreshToken, User user) {
        if ("VALID".equals(validateToken(refreshToken))) {
            // 새 AccessToken 생성
            String newAccessToken = generateAccessToken(user);

            // 새 인증 객체 생성 및 반환
            return getAuthenticationFromToken(newAccessToken);
        }
        return null;
    }

    // Token에서 Authentication 객체 생성
    public Authentication getAuthenticationFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        Long userId = claims.get("user_id", Long.class);
        String email = claims.get("email", String.class);

        CustomUserDetails userDetails = new CustomUserDetails(userId, email, new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }
}
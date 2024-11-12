package LearnMate.dev.security.jwt;

import LearnMate.dev.common.ErrorStatus;
import LearnMate.dev.common.exception.ApiException;
import LearnMate.dev.model.entity.User;
import LearnMate.dev.security.security.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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

    public String generateAccessToken(User user) {
        Date expiredAt = new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME);
        return Jwts.builder()
                .claim("user_id", user.getId())
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(User user) {
        Date expiredAt = new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME);
        return Jwts.builder()
                .claim("user_id", user.getId())
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long getUserId(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("user_id", Long.class);
    }

    public void setAccessTokenInCookie(User user, String accessToken, HttpServletResponse response) {
        // accessToken을 쿠키에 설정
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);   // 클라이언트 측 접근 방지
        accessTokenCookie.setSecure(false);     // HTTPS에서만 전송
        accessTokenCookie.setPath("/");        // 전체 경로에서 접근 가능
        response.addCookie(accessTokenCookie); // 쿠키 설정 추가
    }

    public void storeRefreshTokenInSession(User user, HttpSession session) {
        String refreshToken = generateRefreshToken(user);
        session.setAttribute("refreshToken", refreshToken);
    }

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

    public Authentication getAuthenticationFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        Long userId = claims.get("user_id", Long.class);
        String email = claims.get("email", String.class);

        CustomUserDetails userDetails = new CustomUserDetails(userId, email, new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }


    public Authentication refreshAccessToken(String refreshToken, HttpServletResponse response, User user) {

        if ("VALID".equals(validateToken(refreshToken))) {

            // 새 accessToken 생성
            String newAccessToken = generateAccessToken(user);
            setAccessTokenInCookie(user, newAccessToken, response);

            // 인증 객체 생성 및 반환
            return getAuthenticationFromToken(newAccessToken);
        }
        return null;
    }

}
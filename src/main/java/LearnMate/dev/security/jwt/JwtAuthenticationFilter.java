package LearnMate.dev.security.jwt;

import LearnMate.dev.model.entity.User;
import LearnMate.dev.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String accessToken = extractAccessTokenFromHeader(request);

        if (accessToken != null) {
            String tokenStatus = jwtProvider.validateToken(accessToken);

            if ("VALID".equals(tokenStatus)) {
                // accessToken이 유효한 경우, 인증 정보 설정
                Authentication authentication = jwtProvider.getAuthenticationFromToken(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else if ("EXPIRED".equals(tokenStatus) && session != null) {
                // accessToken이 만료된 경우, refreshToken으로 새로운 accessToken 발급
                String refreshToken = (String) session.getAttribute("refreshToken");

                if (refreshToken != null) {
                    Long userId = jwtProvider.getUserId(refreshToken);
                    User user = userService.findUserById(userId);
                    Authentication authentication = jwtProvider.refreshAccessToken(refreshToken, response, user);

                    if (authentication != null) {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        SecurityContextHolder.clearContext();
                    }
                }
            } else if ("INVALID".equals(tokenStatus)) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 헤더에서 AccessToken 추출
     * @param request HttpServletRequest
     * @return AccessToken (없으면 null)
     */
    private String extractAccessTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후의 토큰 값만 추출
        }
        return null;
    }
}
package LearnMate.dev.security.jwt;

import LearnMate.dev.common.ErrorStatus;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 세션이 있는지 확인
        HttpSession session = request.getSession(false);
        if (session == null) {
            request.setAttribute("exception", ErrorStatus._JWT_NOT_FOUND);
            log.info("세션 없음: JWT 토큰을 찾을 수 없습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        // 세션에서 accessToken 가져오기
        String accessToken = jwtProvider.getAccessTokenFromSession(session);
        if (accessToken == null) {
            request.setAttribute("exception", ErrorStatus._JWT_NOT_FOUND);
            log.info("세션에 AccessToken 없음");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 검증 및 인증 설정
        String validationResult = jwtProvider.validateToken(session);
        if ("VALID".equals(validationResult)) {
            Authentication authentication = jwtProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("로그인 성공: SecurityContext에 인증 설정 완료");
        } else {
            handleInvalidToken(request, validationResult); // 유효하지 않은 토큰 처리
        }

        filterChain.doFilter(request, response);
    }

    // 유효하지 않은 토큰에 대한 예외 설정
    private void handleInvalidToken(HttpServletRequest request, String validationResult) {
        switch (validationResult) {
            case "INVALID":
                log.info("유효하지 않은 AccessToken");
                request.setAttribute("exception", ErrorStatus._JWT_INVALID);
                break;
            case "EXPIRED":
                log.info("만료된 AccessToken");
                request.setAttribute("exception", ErrorStatus._JWT_EXPIRED);
                break;
            default:
                log.info("알 수 없는 오류");
                request.setAttribute("exception", ErrorStatus._JWT_UNKNOWN_ERROR);
                break;
        }
    }
}
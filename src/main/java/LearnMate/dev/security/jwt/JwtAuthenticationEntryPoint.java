package LearnMate.dev.security.jwt;

import LearnMate.dev.common.ErrorStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        ErrorStatus exception = (ErrorStatus) request.getAttribute("exception");

        if (exception == null) {
            log.info("No exception found, setting default error");
            exception = ErrorStatus._JWT_UNKNOWN_ERROR;  // 기본 예외 상태 설정
        }

        log.info("JwtAuthenticationEntryPoint - Exception Control : " + exception);

        if (exception.equals(ErrorStatus._JWT_NOT_FOUND)) {
            exceptionHandler(response, ErrorStatus._JWT_NOT_FOUND, HttpServletResponse.SC_UNAUTHORIZED);
        } else if (exception.equals(ErrorStatus._JWT_INVALID)) {
            exceptionHandler(response, ErrorStatus._JWT_INVALID, HttpServletResponse.SC_UNAUTHORIZED);
        } else if (exception.equals(ErrorStatus._JWT_EXPIRED)) {
            exceptionHandler(response, ErrorStatus._JWT_EXPIRED, HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            exceptionHandler(response, ErrorStatus._JWT_UNKNOWN_ERROR, HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private void exceptionHandler(HttpServletResponse response, ErrorStatus errorStatus, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{ \"error\": \"" + errorStatus.name() + "\" }");
    }
}
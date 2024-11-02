package LearnMate.dev.security.jwt;

import LearnMate.dev.common.ApiException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static LearnMate.dev.utils.ExceptionHandlerUtil.exceptionHandler;

@Component
@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ApiException e) {
            log.info("===================== ExceptionFilter - Exception Control");
            try {
                exceptionHandler(response, e.getErrorStatus(), HttpServletResponse.SC_UNAUTHORIZED);
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

}
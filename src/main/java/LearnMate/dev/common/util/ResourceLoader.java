package LearnMate.dev.common.util;

import LearnMate.dev.common.status.ErrorStatus;
import LearnMate.dev.common.exception.ApiException;
import org.springframework.core.io.ClassPathResource;

import java.nio.charset.StandardCharsets;

public class ResourceLoader {

    public static String getResourceContent(String resourcePath) {
        try {
            var resource = new ClassPathResource(resourcePath);
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new ApiException(ErrorStatus._PROMPT_LOAD_FAILED);
        }
    }
}
package LearnMate.dev.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.language.v1.LanguageServiceSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class GoogleNaturalLanguageConfig {
    @Value("classpath:nlp.json")
    Resource gcsCredentials;

    @Bean
    public LanguageServiceSettings languageServiceSettings() {
        try {
            return LanguageServiceSettings.newBuilder()
                    .setCredentialsProvider(() ->
                            GoogleCredentials.fromStream(gcsCredentials.getInputStream()))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize LanguageServiceSettings", e);
        }
    }
}

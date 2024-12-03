package LearnMate.dev.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.language.v1.LanguageServiceSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Slf4j
@Configuration
public class GoogleNaturalLanguageConfig {
    @Value("${GCP_CREDENTIALS_LOCATION}")
    Resource gcpCredentials;

    @Bean
    public LanguageServiceSettings languageServiceSettings() {
        try {
            log.info("Loading Google Natural Language Service");
            return LanguageServiceSettings.newBuilder()
                    .setCredentialsProvider(() ->
                            GoogleCredentials.fromStream(gcpCredentials.getInputStream()))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize LanguageServiceSettings", e);
        }
    }
}

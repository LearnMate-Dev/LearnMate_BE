package LearnMate.dev.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.language.v1.LanguageServiceSettings;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Configuration
public class GoogleNaturalLanguageConfig {
    @Value("${GCP_CREDENTIALS_LOCATION}")
    private String gcpCredentials;

    @PostConstruct
    public void checkGcpCredentials() {
        log.info("Environment variable GCP_CREDENTIALS_LOCATION: {}", System.getenv("GCP_CREDENTIALS_LOCATION"));
        log.info("Configured GCP_CREDENTIALS_LOCATION: {}", gcpCredentials);
    }

    @Bean
    public LanguageServiceSettings languageServiceSettings() {
        try {
            log.info("Loading GCP credentials from: {}", gcpCredentials);
            File credentialsFile = new File(gcpCredentials);
            if (!credentialsFile.exists()) {
                throw new IOException("GCP credentials file not found at: " + gcpCredentials);
            }

            return LanguageServiceSettings.newBuilder()
                    .setCredentialsProvider(() ->
                            GoogleCredentials.fromStream(new FileInputStream(credentialsFile)))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize LanguageServiceSettings", e);
        }
    }
}

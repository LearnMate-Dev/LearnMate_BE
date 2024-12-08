package LearnMate.dev.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.LanguageServiceSettings;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Configuration
public class GoogleNaturalLanguageConfig {
    @Value("${GOOGLE_APPLICATION_CREDENTIALS}")
    private String gcpCredentials;

    @PostConstruct
    public void checkGcpCredentials() {
        log.info("Environment variable GCP_CREDENTIALS_LOCATION: {}", System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
        log.info("Configured GCP_CREDENTIALS_LOCATION: {}", gcpCredentials);
    }

    @Bean
    public GoogleCredentials googleCredentials() throws IOException {
        // 환경 변수 GCP_CREDENTIALS_LOCATION에서 경로 가져오기
        if (gcpCredentials == null) {
            throw new IllegalArgumentException("환경 변수 GCP_CREDENTIALS_LOCATION이 설정되지 않았습니다.");
        }

        // GoogleCredentials 생성
        return GoogleCredentials.fromStream(new FileInputStream(gcpCredentials));
    }

    @Bean
    public LanguageServiceClient languageServiceClient(GoogleCredentials credentials) throws IOException {
        // LanguageServiceSettings에 Credentials 적용
        LanguageServiceSettings settings = LanguageServiceSettings.newBuilder()
                .setCredentialsProvider(() -> credentials)
                .build();

        // LanguageServiceClient 생성
        return LanguageServiceClient.create(settings);
    }
}

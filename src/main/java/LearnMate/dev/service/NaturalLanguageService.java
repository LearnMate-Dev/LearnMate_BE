package LearnMate.dev.service;

import LearnMate.dev.common.exception.ApiException;
import LearnMate.dev.common.status.ErrorStatus;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class NaturalLanguageService {

    /*
     * cloud Natural Language API를 호출해 텍스트에 대한 감정을 분석
     * @param text
     * @return
     */
    @Async
    public CompletableFuture<Float> analyzeEmotion(String text) {
        try (LanguageServiceClient language = LanguageServiceClient.create()) {

            Document doc = Document.newBuilder().setContent(text).setType(Document.Type.PLAIN_TEXT).build();
            Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();

            System.out.printf("Text: %s%n", text);
            System.out.printf("Sentiment: %s, %s%n", sentiment.getScore(), sentiment.getMagnitude());

            return CompletableFuture.completedFuture(sentiment.getScore());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ApiException(ErrorStatus._ANALYZE_EMOTION_ERROR);
        }
    }
}

package LearnMate.dev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class OpenAIService {

    private OpenAiChatModel chatModel;

    @Async
    public CompletableFuture<String> getActionTip(String content) {
        String text = "Action Tip";

        return CompletableFuture.completedFuture(text);
    }
}

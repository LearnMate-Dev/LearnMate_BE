package LearnMate.dev.service;

import LearnMate.dev.common.util.ResourceLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class OpenAIService {
    private final String ACTION_TIP_PROMPT = ResourceLoader.getResourceContent("action-tip-prompt.txt");
    private final OpenAiChatModel chatModel;

    @Async
    public CompletableFuture<String> getActionTip(String content) {

        String text = chatModel.call(ACTION_TIP_PROMPT + content);

        return CompletableFuture.completedFuture(text);
    }
}

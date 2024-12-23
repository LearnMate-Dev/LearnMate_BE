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
    private final String TODO_GUIDE_PROMPT = ResourceLoader.getResourceContent("todo-guide-prompt.txt");
    private final String COMPLIMENT_CARD_KEYWORD_PROMPT = ResourceLoader.getResourceContent("compliment-card-keyword-prompt.txt");

    private final OpenAiChatModel chatModel;

    @Async
    public CompletableFuture<String> getActionTip(String content) {

        String text = chatModel.call(ACTION_TIP_PROMPT + content);

        return CompletableFuture.completedFuture(text);
    }

    public String getTodoGuide(String content) {

        return chatModel.call(TODO_GUIDE_PROMPT + content);
    }

    @Async
    public CompletableFuture<String> getComplimentCard(String content) {

        String keyword = chatModel.call(COMPLIMENT_CARD_KEYWORD_PROMPT + content);

        return CompletableFuture.completedFuture(keyword);
    }
}

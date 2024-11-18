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
    private final String COMPLIMENT_CARD_TITLE_PROMPT = ResourceLoader.getResourceContent("compliment-card-title-prompt.txt");
    private final String COMPLIMENT_CARD_CONTENT_PROMPT = ResourceLoader.getResourceContent("compliment-card-content-prompt.txt");

    private final OpenAiChatModel chatModel;

    @Async
    public CompletableFuture<String> getActionTip(String content) {

        String text = chatModel.call(ACTION_TIP_PROMPT + content);

        return CompletableFuture.completedFuture(text);
    }


    public String getTodoGuide(String content) {

        return chatModel.call(TODO_GUIDE_PROMPT + content);

    }

    public String getComplimentCardTitle(String content) {

        return chatModel.call(COMPLIMENT_CARD_TITLE_PROMPT + content);

    }

    public String getComplimentCardContent(String content) {

        return chatModel.call(COMPLIMENT_CARD_CONTENT_PROMPT + content);

    }

}

package LearnMate.dev.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ActionTipService {

    @Async
    public CompletableFuture<String> getActionTip(String content) {
        String text = "Action Tip";

        return CompletableFuture.completedFuture(text);
    }
}

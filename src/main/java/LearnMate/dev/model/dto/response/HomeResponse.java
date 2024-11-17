package LearnMate.dev.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class HomeResponse {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HomeDto {

        private Long diaryId;
        private String emoticon;
        private Long todoId;
        private String todoGuide;

    }
}

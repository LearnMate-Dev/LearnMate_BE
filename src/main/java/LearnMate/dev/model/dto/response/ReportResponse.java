package LearnMate.dev.model.dto.response;

import LearnMate.dev.model.enums.EmotionSpectrum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ReportResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmotionDto {

        private EmotionSpectrum emotionSpectrum;
        private Long count;
        private int order;

        public EmotionDto(EmotionSpectrum emotion, Long count) {
            this.emotionSpectrum = emotion;
            this.count = count == null ? 0 : count;
            this.order = emotion.getOrder();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReportEmotionDto {

        private String emotion;
        private String emoticon;
        private Long count;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReportDto {

        private List<ReportEmotionDto> emotionReport;

    }

}



package LearnMate.dev.model.dto.response.diary;

import LearnMate.dev.model.enums.EmotionSpectrum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DiaryCalendarResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DiaryDto {

        private Long diaryId;
        private String date;
        private String emoticon;

        public DiaryDto(Long diaryId, LocalDateTime date, EmotionSpectrum emoticon) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            this.diaryId = diaryId;
            this.date = date.format(formatter);
            this.emoticon = emoticon.getEmoticon();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DiaryCalendarDto {

        private List<DiaryDto> diaryCalendar;

    }
}

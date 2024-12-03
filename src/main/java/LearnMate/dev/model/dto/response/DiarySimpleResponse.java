package LearnMate.dev.model.dto.response;

import LearnMate.dev.model.enums.EmotionSpectrum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiarySimpleResponse {

    private Long diaryId;
    private String date;
    private String emoticon;
    private String content;

    public DiarySimpleResponse(Long diaryId, LocalDateTime date, EmotionSpectrum emoticon, String content) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        this.diaryId = diaryId;
        this.date = date.format(formatter);
        this.emoticon = emoticon.getEmoticon();
        this.content = content;
    }
}

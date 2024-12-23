package LearnMate.dev.model.converter;

import LearnMate.dev.model.dto.response.diary.DiaryAnalysisResponse;
import LearnMate.dev.model.dto.response.diary.DiaryCalendarResponse;
import LearnMate.dev.model.dto.response.diary.DiaryDetailResponse;
import LearnMate.dev.model.entity.*;

import java.util.List;

public class DiaryConverter {

    public static Diary toDiary(String content, User user, Emotion emotion,
                                ActionTip actionTip, ComplimentCard complimentCard) {
        return Diary.builder()
                .content(content)
                .user(user)
                .emotion(emotion)
                .actionTip(actionTip)
                .complimentCard(complimentCard)
                .build();
    }

    public static DiaryDetailResponse toDiaryDetailResponse(Diary diary) {
        return DiaryDetailResponse.builder()
                .diaryId(diary.getId())
                .date(diary.getCreatedAtFormatted())
                .content(diary.getContent())
                .emotion(diary.getEmotion().getEmotion().getValue())
                .actionTip(diary.getActionTip().getContent())
                .compliment(diary.getComplimentCard().getKeyword().getValue())
                .build();
    }

    public static DiaryAnalysisResponse toDiaryAnalysisResponse(Float score, String actionTip, String compliment) {
        return DiaryAnalysisResponse.builder()
                .emotionScore(score)
                .actionTip(actionTip)
                .compliment(compliment)
                .build();
    }

    public static DiaryCalendarResponse.DiaryCalendarDto toDiaryCalendarResponse(List<DiaryCalendarResponse.DiaryDto> diaryDtoList) {
        return DiaryCalendarResponse.DiaryCalendarDto.builder()
                .diaryCalendar(diaryDtoList)
                .build();
    }
}

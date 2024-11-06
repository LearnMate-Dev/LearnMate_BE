package LearnMate.dev.model.converter;

import LearnMate.dev.model.dto.response.DiaryAnalysisResponse;
import LearnMate.dev.model.dto.response.DiaryDetailResponse;
import LearnMate.dev.model.entity.ActionTip;
import LearnMate.dev.model.entity.Diary;
import LearnMate.dev.model.entity.Emotion;
import LearnMate.dev.model.entity.User;

public class DiaryConverter {

    public static Diary toDiary(String content, User user, Emotion emotion, ActionTip actionTip) {
        return Diary.builder()
                .content(content)
                .user(user)
                .emotion(emotion)
                .actionTip(actionTip)
                .build();
    }

    public static DiaryDetailResponse toDiaryDetailResponse(Diary diary) {
        return DiaryDetailResponse.builder()
                .date(diary.getCreatedAtFormatted())
                .content(diary.getContent())
                .emotion(diary.getEmotion().getEmotion().getValue())
                .actionTip(diary.getActionTip().getContent())
                .build();
    }

    public static DiaryAnalysisResponse toDiaryAnalysisResponse(Double score, String actionTip) {
        return DiaryAnalysisResponse.builder()
                .emotionScore(score)
                .actionTip(actionTip)
                .build();
    }


}

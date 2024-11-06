package LearnMate.dev.model.converter;

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


}

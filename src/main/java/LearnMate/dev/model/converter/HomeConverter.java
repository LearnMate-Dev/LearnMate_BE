package LearnMate.dev.model.converter;

import LearnMate.dev.model.dto.response.home.HomeResponse;
import LearnMate.dev.model.entity.Diary;
import LearnMate.dev.model.entity.Plan;

public class HomeConverter {
    public static HomeResponse toHomeResponse(Diary diary, Plan plan) {
        return HomeResponse.builder()
                .diaryId(diary == null ? null : diary.getId())
                .emoticon(diary == null ? null : diary.getEmotion().getEmotion().getEmoticon())
                .todoId(plan == null ? null : plan.getId())
                .todoGuide(plan == null ? null : plan.getGuide())
                .build();
    }
}

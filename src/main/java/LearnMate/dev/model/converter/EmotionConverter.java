package LearnMate.dev.model.converter;

import LearnMate.dev.model.entity.Emotion;
import LearnMate.dev.model.enums.EmotionSpectrum;

public class EmotionConverter {
    public static Emotion toEmotion(Double score, EmotionSpectrum emotion) {
        return Emotion.builder()
                .score(score)
                .emotion(emotion)
                .build();
    }
}

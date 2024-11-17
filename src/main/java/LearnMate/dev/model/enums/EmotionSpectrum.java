package LearnMate.dev.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum EmotionSpectrum {

    HAPPY("행복", "🥰", 1), DELIGHT("기쁨", "🥳", 2), EXCITING("신남", "😝", 3),
    SOSO("보통", "😐", 4),
    ANNOYING("짜증", "😫", 5), SAD("슬픔", "😢", 6), ANGRY("화남", "😡", 7);

    public final String value;
    public final String emoticon;
    public final int order;

    public static EmotionSpectrum getName(String value){
        for (EmotionSpectrum emotion : EmotionSpectrum.values()) {
            if (emotion.getValue().equals(value)) {
                return emotion;
            }
        }
        return null;
    }

}

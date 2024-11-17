package LearnMate.dev.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum EmotionSpectrum {

    HAPPY("행복", "🥰"), DELIGHT("기쁨", "🥳"), EXCITING("신남", "😝"),
    SOSO("보통", "😐"),
    ANNOYING("짜증", "😫"), SAD("슬픔", "😢"), ANGRY("화남", "😡");

    public final String value;
    public final String emoticon;

    public static EmotionSpectrum getName(String value){
        for (EmotionSpectrum emotion : EmotionSpectrum.values()) {
            if (emotion.getValue().equals(value)) {
                return emotion;
            }
        }
        return null;
    }

}

package LearnMate.dev.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EmotionSpectrum {

    ANGRY("화남"), SAD("슬픔"), ANNOYING("짜증"),
    SOSO("보통"), HAPPY("행복"), DELIGHT("기쁨"), EXCITING("신남") ;

    public final String value;

    public String getValue() {
        return value;
    }

    public static EmotionSpectrum getName(String value){
        for (EmotionSpectrum emotion : EmotionSpectrum.values()) {
            if (emotion.getValue().equals(value)) {
                return emotion;
            }
        }
        return null;
    }

}

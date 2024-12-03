package LearnMate.dev.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ComplimentKeyword {

    EFFORT("노력", "노력 Content"), PASSION("열정", "열정 Content"),
    TRUTH("성실", "성실 Content"), RESPONSIBILITY("책임감", "책임감 Content"),
    CREATIVITY("창의력", "창의력 Content"), DELICATE("섬세함", "섬세함 Content"),
    COURAGE("용기", "용기 Content"), KINDNESS("친절함", "친절함 Content"),
    CONSIDERATION("배려심", "배려심 Content"), AFFIRMATION("긍정", "긍정 Content")
    ;

    private final String value;
    private final String content;

    public static ComplimentKeyword getKeyword(String value){
        for (ComplimentKeyword keyword : ComplimentKeyword.values()) {
            if (keyword.getValue().equals(value)) {
                return keyword;
            }
        }
        return null;
    }
}
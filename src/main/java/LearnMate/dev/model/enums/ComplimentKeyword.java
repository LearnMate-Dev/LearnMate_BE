package LearnMate.dev.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ComplimentKeyword {

    EFFORT("노력", "어려운 상황에서도 포기하지 않고 끝까지 해내는 당신의 노력은 정말 대단해요. 계속 응원할게요!"),
    PASSION("열정", "당신의 뜨거운 열정이 더 큰 가능성을 만들어가고 있어요. 앞으로도 그 열정 잃지 않길 바라요!”"),
    TRUTH("성실", "늘 최선을 다하고 성실하게 임하는 모습에서 큰 감동을 받습니다. 그 꾸준한 노력이 분명 큰 성과로 이어질 거예요.”"),
    RESPONSIBILITY("책임감", "맡은 일을 끝까지 책임지는 모습이 정말 믿음직스럽고 멋져요. 당신 덕분에 모두가 안심할 수 있습니다."),
    CREATIVITY("창의력", "당신의 독창적인 생각 덕분에 주변이 더욱 다채롭고 풍요로워집니다. 앞으로도 그 상상력을 마음껏 펼치길 바라요!"),
    DELICATE("섬세함", "작은 부분까지도 세심히 신경 쓰는 당신의 섬세함 덕분에 모두가 더 편안함을 느낍니다. 그 따뜻한 마음에 감사드려요!”"),
    COURAGE("용기", "어려운 순간에도 용기를 잃지 않고 앞으로 나아가는 모습이 정말 멋져요. 당신의 도전, 항상 응원할게요!"),
    KINDNESS("친절함", "언제나 따뜻한 말과 행동으로 주변 사람들을 행복하게 해주는 당신의 친절함이 정말 아름다워요. 당신 덕분에 모두가 더 밝아지고 행복해져요."),
    CONSIDERATION("배려심", "당신의 세심한 배려 덕분에 모두가 더 행복하고 편안해졌어요. 당신과 함께하는 시간이 정말 따뜻합니다.”"),
    AFFIRMATION("긍정", "긍정적인 태도로 모든 걸 더 나은 방향으로 이끄는 당신 덕분에 모두가 힘을 얻습니다. 앞으로도 밝고 긍정적인 에너지로 주변을 환하게 만들어주세요!"),
    NO_KEYWORD("NO_KEYWORD", "X")
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
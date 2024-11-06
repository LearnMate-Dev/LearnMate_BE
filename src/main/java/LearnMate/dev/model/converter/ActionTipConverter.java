package LearnMate.dev.model.converter;

import LearnMate.dev.model.entity.ActionTip;

public class ActionTipConverter {
    public static ActionTip toActionTip(String content) {
        return ActionTip.builder()
                .content(content)
                .build();
    }
}

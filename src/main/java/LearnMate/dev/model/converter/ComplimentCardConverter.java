package LearnMate.dev.model.converter;

import LearnMate.dev.model.dto.response.compliment.ComplimentCardListResponse;
import LearnMate.dev.model.dto.response.compliment.ComplimentCardResponse;
import LearnMate.dev.model.entity.ComplimentCard;
import LearnMate.dev.model.entity.User;
import LearnMate.dev.model.enums.ComplimentKeyword;

import java.util.List;

public class ComplimentCardConverter {

    public static ComplimentCard toComplimentCard(ComplimentKeyword keyword, User user) {
        return ComplimentCard.builder()
                .keyword(keyword)
                .user(user)
                .build();
    }

    public static List<ComplimentCardListResponse> toComplimentCardListResponseList(List<ComplimentCard> complimentCards) {

        return complimentCards.stream()
                .map(complimentCard -> ComplimentCardListResponse.builder()
                        .complimentId(complimentCard.getId())
                        .complimentKeyword(complimentCard.getKeyword().getValue())
                        .build())
                .toList();
    }

    public static ComplimentCardResponse toComplimentCardResponse(ComplimentCard complimentCard) {
        return ComplimentCardResponse.builder()
                .complimentId(complimentCard.getId())
                .complimentKeyword(complimentCard.getKeyword().getValue())
                .complimentContent(complimentCard.getKeyword().getContent())
                .build();
    }
}

package LearnMate.dev.model.converter;

import LearnMate.dev.model.dto.response.ComplimentCardListResponse;
import LearnMate.dev.model.dto.response.ComplimentCardResponse;
import LearnMate.dev.model.entity.ComplimentCard;
import LearnMate.dev.model.enums.ComplimentKeyword;

import java.util.List;

public class ComplimentCardConverter {

    public static ComplimentCard toComplimentCard(ComplimentKeyword keyword) {
        return ComplimentCard.builder()
                .keyword(keyword)
                .build();
    }

    public static List<ComplimentCardListResponse> toComplimentCardListResponseList(List<ComplimentCard> complimentCards) {

        return complimentCards.stream()
                .map(complimentCard -> ComplimentCardListResponse.builder()
                        .complimentId(complimentCard.getId())
                        .build())
                .toList();

    }
}

package LearnMate.dev.model.converter;

import LearnMate.dev.model.dto.response.ComplimentCardListResponse;
import LearnMate.dev.model.dto.response.ComplimentCardResponse;
import LearnMate.dev.model.entity.ComplimentCard;
import LearnMate.dev.model.entity.User;

import java.util.List;

public class ComplimentCardConverter {

    public static ComplimentCard toComplimentCard(User user,
                                                  String complimentTitle,
                                                  String complimentContent,
                                                  Long diaryId) {
        return ComplimentCard.builder()
                .user(user)
                .title(complimentTitle)
                .content(complimentContent)
                .diaryId(diaryId)
                .build();
    }

    public static List<ComplimentCardListResponse> toComplimentCardListResponseList(List<ComplimentCard> complimentCards) {

        return complimentCards.stream()
                .map(complimentCard -> ComplimentCardListResponse.builder()
                        .complimentId(complimentCard.getId())
                        .complimentTitle(complimentCard.getTitle())
                        .complimentContent(complimentCard.getContent())
                        .build())
                .toList();

    }

    public static ComplimentCardResponse toComplimentCardResponse(ComplimentCard complimentCard) {
        return ComplimentCardResponse.builder()
                .complimentTitle(complimentCard.getTitle())
                .complimentContent(complimentCard.getContent())
                .diaryId(complimentCard.getDiaryId())
                .build();
    }

}

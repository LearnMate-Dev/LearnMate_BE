package LearnMate.dev.service;

import LearnMate.dev.common.ErrorStatus;
import LearnMate.dev.common.exception.ApiException;
import LearnMate.dev.model.converter.ComplimentCardConverter;
import LearnMate.dev.model.dto.response.ComplimentCardListResponse;
import LearnMate.dev.model.dto.response.ComplimentCardResponse;
import LearnMate.dev.model.entity.ComplimentCard;
import LearnMate.dev.model.entity.User;
import LearnMate.dev.model.enums.ComplimentKeyword;
import LearnMate.dev.repository.ComplimentCardRepository;
import LearnMate.dev.security.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ComplimentCardService {

    private final ComplimentCardRepository complimentCardRepository;
    private final OpenAIService openAIService;


    // 칭찬카드 생성 및 조회
    @Transactional
    public ComplimentCard createComplimentCard(String content, User user) {

        // 칭찬 keyword 파싱
        String keywordValue = openAIService.getComplimentCard(content);
        ComplimentKeyword keyword = getComplimentKeyword(keywordValue);

        // compliment card entity 조회 및 생성
        ComplimentCard complimentCard = findComplimentCardByKeyword(keyword, user);
        complimentCardRepository.save(complimentCard);

        return complimentCard;
    }

    private ComplimentCard findComplimentCardByKeyword(ComplimentKeyword keyword, User user) {
        return complimentCardRepository.findByKeywordAndUser(keyword, user)
                .orElse(ComplimentCardConverter.toComplimentCard(keyword, user));
    }

    private ComplimentKeyword getComplimentKeyword(String keywordValue) {
        ComplimentKeyword keyword = ComplimentKeyword.getKeyword(keywordValue);

        if (keyword == null) {
            throw new ApiException(ErrorStatus._INVALID_COMPLIMENT_KEYWORD);
        }

        return keyword;
    }

    // 칭찬카드 리스트 조회
    public List<ComplimentCardListResponse> getComplimentCards() {

        Long userId = getUserIdFromAuthentication();

        List<ComplimentCard> complimentCards = findComplimentCardsByUserId(userId);

        return ComplimentCardConverter.toComplimentCardListResponseList(complimentCards);

    }

    // 칭찬카드 상세 조회
    public ComplimentCardResponse getComplimentCardDetail(Long complimentId) {

        Long userId = getUserIdFromAuthentication();
        ComplimentCard complimentCard = findComplimentCardById(complimentId);

        validIsUserAuthorizedForComplimentCard(userId, complimentCard);

        return ComplimentCardConverter.toComplimentCardResponse(complimentCard);
    }

    private void validIsUserAuthorizedForComplimentCard(Long userId, ComplimentCard complimentCard) {
        if (!complimentCard.getUser().getId().equals(userId)) {
            throw new ApiException(ErrorStatus._USER_FORBIDDEN_COMPLIMENT_CARD);
        }
    }

    // 칭찬카드 관련 일기 리스트 조회


    private Long getUserIdFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUserId();
    }

    private ComplimentCard findComplimentCardById(Long complimentId) {
        return complimentCardRepository.findById(complimentId)
                .orElseThrow(() -> new ApiException(ErrorStatus._COMPLIMENT_CARD_NOT_FOUND));
    }

    private List<ComplimentCard> findComplimentCardsByUserId(Long userId) {
        return complimentCardRepository.findAllByUserId(userId);
    }
}

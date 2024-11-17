package LearnMate.dev.service;

import LearnMate.dev.common.ErrorStatus;
import LearnMate.dev.common.exception.ApiException;
import LearnMate.dev.model.converter.ReportConverter;
import LearnMate.dev.model.dto.response.ReportResponse;
import LearnMate.dev.model.entity.User;
import LearnMate.dev.repository.EmotionRepository;
import LearnMate.dev.repository.UserRepository;
import LearnMate.dev.security.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final UserRepository userRepository;
    private final EmotionRepository emotionRepository;


    /*
     * user가 2주동안 겪은 각 감정의 개수를 리스트 형태로 반환
     * @return
     */
    public ReportResponse.ReportDto getEmotionReport() {
        Long userId = getUserIdFromAuthentication();
        User user = findUserById(userId);

        // 2주동안 나타난 각 감정의 개수 반환
        LocalDate now = LocalDate.now();
        LocalDate twoWeekAgo = now.minusDays(14);
        List<ReportResponse.EmotionDto> emotionDtoList = getEmotionDtoList(twoWeekAgo, now, user);

        // emotion 순서대로 정렬
        emotionDtoList = emotionDtoList.stream()
                .sorted(Comparator.comparingInt(ReportResponse.EmotionDto::getOrder))
                .collect(Collectors.toList());

        return ReportConverter.toReportDto(emotionDtoList);
    }

    private Long getUserIdFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUserId();
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
    }

    private List<ReportResponse.EmotionDto> getEmotionDtoList(LocalDate startDate, LocalDate endDate, User user) {
        return emotionRepository.findEmotionDtoByUser(startDate, endDate, user);
    }
}

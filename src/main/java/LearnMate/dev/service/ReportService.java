package LearnMate.dev.service;

import LearnMate.dev.common.ErrorStatus;
import LearnMate.dev.common.exception.ApiException;
import LearnMate.dev.model.converter.ReportConverter;
import LearnMate.dev.model.dto.response.ReportResponse;
import LearnMate.dev.model.entity.User;
import LearnMate.dev.model.enums.EmotionSpectrum;
import LearnMate.dev.repository.EmotionRepository;
import LearnMate.dev.repository.UserRepository;
import LearnMate.dev.security.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

        // 각 감정에 대한 결과 emotiondto를 mapping
        Map<EmotionSpectrum, ReportResponse.EmotionDto> emotionDtoMap = emotionDtoList.stream()
                .collect(Collectors.toMap(ReportResponse.EmotionDto::getEmotionSpectrum, dto -> dto));

        // 0개 나타난 emotion dto 추가 및 정렬
        List<ReportResponse.EmotionDto> completeEmotionList = Arrays.stream(EmotionSpectrum.values())
                .map(emotionSpectrum -> emotionDtoMap.getOrDefault(
                        emotionSpectrum,
                        new ReportResponse.EmotionDto(emotionSpectrum, 0L) // 기본값으로 초기화된 DTO
                ))
                .sorted(Comparator.comparingInt(ReportResponse.EmotionDto::getOrder)) // 정렬
                .toList();

        return ReportConverter.toReportDto(completeEmotionList);
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

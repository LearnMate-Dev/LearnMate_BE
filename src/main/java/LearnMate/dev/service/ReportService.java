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
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final UserRepository userRepository;
    private final EmotionRepository emotionRepository;


    /*
     * user의 2주동안 나타난 각 감정의 개수를 리스트 형태로 반환
     * @return
     */
    public ReportResponse.ReportDto getEmotionReport() {
        Long userId = getUserIdFromAuthentication();
        User user = findUserById(userId);

        LocalDate now = LocalDate.now();
        LocalDate twoWeekAgo = now.minusDays(14);

        // 2주동안 나타난 각 감정의 개수 반환
        List<ReportResponse.EmotionDto> emotionDtoList = getEmotionDtoList(twoWeekAgo, now, user);
        List<ReportResponse.ReportEmotionDto> reportEmotionDtoList = ReportConverter.toReportEmotionDtoList(emotionDtoList);

        return ReportConverter.toReportDto(reportEmotionDtoList);
    }

    /*
     * user의 2주동안 나타난 각 감정의 개수, 각 날짜에 나타난 감정, 가장 많이 나타난 감정 리스트를 반환
     * @return
     */
    public ReportResponse.ReportDetailDto getEmotionDetailReport() {
        Long userId = getUserIdFromAuthentication();
        User user = findUserById(userId);

        LocalDate now = LocalDate.now();
        LocalDate twoWeekAgo = now.minusDays(14);

        // 각 감정의 개수
        List<ReportResponse.EmotionDto> emotionDtoList = getEmotionDtoList(twoWeekAgo, now, user);
        List<ReportResponse.ReportEmotionDto> reportEmotionDtoList = ReportConverter.toReportEmotionDtoList(emotionDtoList);

        // 각 날짜에 나타난 감정
        List<ReportResponse.EmotionOnDayDto> emotionOnDayDtoList = getEmotionOnDay(twoWeekAgo, now, user);

        // 해당 기간동안 가장 많이 나타난 감정 3개
        List<ReportResponse.EmotionRankDto> emotionRankDtoList = getEmotionRank(reportEmotionDtoList);

        return ReportConverter.toReportDetailDto(reportEmotionDtoList, emotionOnDayDtoList, emotionRankDtoList);
    }

    private List<ReportResponse.EmotionDto> getEmotionDtoList(LocalDate startDate, LocalDate endDate, User user) {
        List<ReportResponse.EmotionDto> emotionDtoList = emotionRepository.findEmotionDtoByUser(startDate, endDate, user);
        return getDefaultEmotionDtoAndParse(emotionDtoList);
    }

    private List<ReportResponse.EmotionDto> getDefaultEmotionDtoAndParse(List<ReportResponse.EmotionDto> emotionDtoList) {
        // 각 감정에 대한 결과 emotiondto를 mapping
        Map<EmotionSpectrum, ReportResponse.EmotionDto> emotionDtoMap = emotionDtoList.stream()
                .collect(Collectors.toMap(ReportResponse.EmotionDto::getEmotionSpectrum, dto -> dto));

        // 0개 나타난 emotion dto 추가 및 정렬
        return Arrays.stream(EmotionSpectrum.values())
                .map(emotionSpectrum -> emotionDtoMap.getOrDefault(
                        emotionSpectrum,
                        new ReportResponse.EmotionDto(emotionSpectrum, 0L) // 기본값으로 초기화된 DTO
                ))
                .sorted(Comparator.comparingInt(ReportResponse.EmotionDto::getOrder)) // 정렬
                .toList();
    }

    private List<ReportResponse.EmotionOnDayDto> getEmotionOnDay(LocalDate startDate, LocalDate endDate, User user) {
        return emotionRepository.findEmotionOnDayDtoByUser(startDate, endDate, user);
    }

    private List<ReportResponse.EmotionRankDto> getEmotionRank(List<ReportResponse.ReportEmotionDto> emotionDtoList) {
        AtomicLong rankCounter = new AtomicLong(1);

        return emotionDtoList.stream()
                // count 기준 내림차순 정렬
                .sorted(Comparator.comparingLong(ReportResponse.ReportEmotionDto::getCount).reversed())
                .limit(3) // 상위 3개만 추출
                .map(dto -> new ReportResponse.EmotionRankDto( // dto 변환
                        dto.getEmotion(),
                        dto.getEmoticon(),
                        rankCounter.getAndIncrement() // 랭크 할당
                ))
                .toList();
    }

    private Long getUserIdFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUserId();
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
    }
}

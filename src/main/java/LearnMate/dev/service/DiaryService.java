package LearnMate.dev.service;

import LearnMate.dev.common.exception.ApiException;
import LearnMate.dev.common.ErrorStatus;
import LearnMate.dev.model.converter.ActionTipConverter;
import LearnMate.dev.model.converter.DiaryConverter;
import LearnMate.dev.model.converter.EmotionConverter;
import LearnMate.dev.model.dto.request.DiaryAnalysisRequest;
import LearnMate.dev.model.dto.request.DiaryPatchRequest;
import LearnMate.dev.model.dto.request.DiaryPostRequest;
import LearnMate.dev.model.dto.response.DiaryAnalysisResponse;
import LearnMate.dev.model.dto.response.DiaryDetailResponse;
import LearnMate.dev.model.entity.ActionTip;
import LearnMate.dev.model.entity.Diary;
import LearnMate.dev.model.entity.Emotion;
import LearnMate.dev.model.entity.User;
import LearnMate.dev.model.enums.EmotionSpectrum;
import LearnMate.dev.repository.DiaryRepository;
import LearnMate.dev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;
    private final NaturalLanguageService naturalLanguageService;
    private final OpenAIService openAIService;

    /*
     * 유저의 일기 내용을 기반으로 감정을 분석하고 행동 요령을 제안함
     * @param userId
     * @param request
     * @return
     */
    public DiaryAnalysisResponse analyzeDiary(Long userId, DiaryAnalysisRequest request) {
        User user = findUserById(userId);
        validIsUserPostDiary(user);

        // content 길이 검사
        String content = request.getContent();
        validContentLength(content);

        // 감정 분석 후 감정 점수 반환
        CompletableFuture<Float> scoreFuture = naturalLanguageService.analyzeEmotion(content);

        // 행동 요령 제안
        CompletableFuture<String> actionTipFuture = openAIService.getActionTip(content);

        // 두 CompletableFuture 조합
        return scoreFuture.thenCombine(actionTipFuture, DiaryConverter::toDiaryAnalysisResponse).join();
    }

    /*
     * content, 감정 분석 결과, 행동 요령 제안 결과를 받아 최종적으로 일기를 작성 후 상세 정보를 반환
     * @param userId
     * @param request
     * @return
     */
    @Transactional
    public DiaryDetailResponse postDiary(Long userId, DiaryPostRequest request) {
        User user = findUserById(userId);
        validIsUserPostDiary(user);

        // content 길이 검사
        validContentLength(request.getContent());

        // emotion 정보 검사
        EmotionSpectrum emotionSpectrum = validEmotion(request.getScore(), request.getEmotion());

        // 객체 생성 및 연관관계 설정
        Diary diary = createDiary(user, request, emotionSpectrum);
        diaryRepository.save(diary);

        return DiaryConverter.toDiaryDetailResponse(diary);
    }

    /*
     * content, diaryId를 받아 해당 일기의 내용을 수정 후 일기 상세 정보를 반환
     * @param userId
     * @param request
     * @return
     */
    @Transactional
    public DiaryDetailResponse patchDiary(Long userId, DiaryPatchRequest request) {
        // user - diary 권한 검사
        User user = findUserById(userId);
        Diary diary = findDiaryById(request.getDiaryId());
        validIsUserAuthorizedForDiary(user, diary);

        // 일기 작성 날짜 검사
        validDiaryCreatedAt(diary);

        // content 길이 검사
        String content = request.getContent();
        validContentLength(content);

        // content 정보 수정
        diary.updateContent(content);

        // TODO: 감정 분석 API 호출

        return DiaryConverter.toDiaryDetailResponse(diary);
    }

    /*
     * diaryId를 param으로 받아, diary, emotion, actiontip 객체 제거
     * @param userId
     * @param diaryId
     */
    @Transactional
    public void deleteDiary(Long userId, Long diaryId) {
        // user - diary 권한 검사
        User user = findUserById(userId);
        Diary diary = findDiaryById(diaryId);
        validIsUserAuthorizedForDiary(user, diary);

        diaryRepository.delete(diary);
    }

    /*
     * diary 작성 날짜, 내용, 감정 지표, 행동 요령 등 상세 정보를 반환
     * @param userId
     * @param diaryId
     * @return
     */
    public DiaryDetailResponse getDiaryDetail(Long userId, Long diaryId) {
        // user - diary 권한 검사
        User user = findUserById(userId);
        Diary diary = findDiaryById(diaryId);
        validIsUserAuthorizedForDiary(user, diary);

        return DiaryConverter.toDiaryDetailResponse(diary);
    }

    private Diary createDiary(User user, DiaryPostRequest request, EmotionSpectrum emotionSpectrum) {
        Emotion emotion = EmotionConverter.toEmotion(request.getScore(), emotionSpectrum);
        ActionTip actionTip = ActionTipConverter.toActionTip(request.getActionTip());
        Diary diary = DiaryConverter.toDiary(request.getContent(), user, emotion, actionTip);

        emotion.updateDiary(diary);
        actionTip.updateDiary(diary);

        return diary;
    }

    private void validIsUserPostDiary(User user) {
        if (diaryRepository.existsDiaryByCreatedAt(user, LocalDate.now()))
            throw new ApiException(ErrorStatus._DUPLICATE_DIARY_DATE);
    }

    private void validContentLength(String content) {
        if (content.length() > 500)
            throw new ApiException(ErrorStatus._INVALID_DIARY_CONTENT_LENGTH);
    }

    private void validIsUserAuthorizedForDiary(User user, Diary diary) {
        if (!diary.getUser().equals(user))
            throw new ApiException(ErrorStatus._USER_FORBIDDEN_DIARY);
    }

    private void validDiaryCreatedAt(Diary diary) {
        if (diary.getCreatedAt().toLocalDate().equals(LocalDate.now()))
            throw new ApiException(ErrorStatus._INVALID_PATCH_DIARY);
    }

    private EmotionSpectrum validEmotion(Double score, String emotion) {
        if (score < -1.0 || score > 1.0)
            throw new ApiException(ErrorStatus._INVALID_EMOTION_SCORE);

        EmotionSpectrum emotionSpectrum = EmotionSpectrum.getName(emotion);
        if (emotionSpectrum == null)
            throw new ApiException(ErrorStatus._INVALID_EMOTION_SPECTRUN);

        return emotionSpectrum;
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorStatus._ACCOUNT_NOT_FOUND));
    }

    private Diary findDiaryById(Long diaryId) {
        return diaryRepository.findById(diaryId)
                .orElseThrow(() -> new ApiException(ErrorStatus._DIARY_NOT_FOUND));
    }
}

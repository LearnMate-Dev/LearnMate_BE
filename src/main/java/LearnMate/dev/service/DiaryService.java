package LearnMate.dev.service;

import LearnMate.dev.common.ApiException;
import LearnMate.dev.common.ErrorStatus;
import LearnMate.dev.model.converter.DiaryConverter;
import LearnMate.dev.model.dto.request.DiaryAnalysisRequest;
import LearnMate.dev.model.dto.request.DiaryPatchRequest;
import LearnMate.dev.model.dto.request.DiaryPostRequest;
import LearnMate.dev.model.dto.response.DiaryAnalysisResponse;
import LearnMate.dev.model.dto.response.DiaryDetailResponse;
import LearnMate.dev.model.entity.Diary;
import LearnMate.dev.model.entity.User;
import LearnMate.dev.repository.DiaryRepository;
import LearnMate.dev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

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

        // TODO: 감정 분석 API 호출

        // TODO: 행동 요령 제안 API 호출

        return DiaryConverter.toDiaryAnalysisResponse(1.0, "ActionTip");
    }

    /*
     * content를 받아 일기를 작성
     * @param userId
     * @param request
     * @return
     */
    @Transactional
    public Long postDiary(Long userId, DiaryPostRequest request) {
        User user = findUserById(userId);
        validIsUserPostDiary(user);

        // content 길이 검사
        String content = request.getContent();
        validContentLength(content);

        // TODO: GPT API 연결 및 EMOTION, ACTIONTIP ENTITY 저장 수정
        Diary diary = DiaryConverter.toDiary(request.getContent(), user, null, null);
        diaryRepository.save(diary);

        // TODO: 일기 및 감정 분석 상세 조회로 변경
        return diary.getId();
    }

    /*
     * content, diaryId를 받아 해당 일기의 내용을 수정 후 일기 상세 정보를 반환
     * @param userId
     * @param request
     * @return
     */
    @Transactional
    public Long patchDiary(Long userId, DiaryPatchRequest request) {
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

        // TODO: 일기 및 감정 분석 상세 조회로 변경
        return diary.getId();
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

        // TODO: EMOTION, ACTIONTIP CASCADE
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

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorStatus._ACCOUNT_NOT_FOUND));
    }

    private Diary findDiaryById(Long diaryId) {
        return diaryRepository.findById(diaryId)
                .orElseThrow(() -> new ApiException(ErrorStatus._DIARY_NOT_FOUND));
    }
}

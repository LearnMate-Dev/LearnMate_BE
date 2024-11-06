package LearnMate.dev.service;

import LearnMate.dev.common.ApiException;
import LearnMate.dev.common.ErrorStatus;
import LearnMate.dev.model.converter.DiaryConverter;
import LearnMate.dev.model.dto.request.DiaryPostRequest;
import LearnMate.dev.model.entity.Diary;
import LearnMate.dev.model.entity.User;
import LearnMate.dev.repository.DiaryRepository;
import LearnMate.dev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    public Long postDiary(Long userId, DiaryPostRequest request) {
        User user = findUserById(userId);

        // TODO: GPT API 연결 및 EMOTION, ACTIONTIP ENTITY 저장 수정
        Diary diary = DiaryConverter.toDiary(request.getContent(), user, null, null);
        diaryRepository.save(diary);

        // TODO: 일기 및 감정 분석 상세 조회로 변경
        return diary.getId();
    }


    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorStatus._ACCOUNT_NOT_FOUND));
    }
}

package LearnMate.dev.common;

import LearnMate.dev.model.BaseErrorCode;
import LearnMate.dev.model.dto.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    _ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCOUNT404", "해당 계정을 찾을 수 없습니다."),
    _ACCOUNT_NOT_EXIST(HttpStatus.NOT_FOUND, "ACCOUNT404", "가입되지 않은 이메일 주소입니다."),
    _ACCOUNT_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "ACCOUNT400", "이미 존재하는 계정입니다."),
    _WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "ACCOUNT400", "비밀번호가 일치하지 않습니다."),
    _SAME_PASSWORD(HttpStatus.BAD_REQUEST, "ACCOUNT400", "기존 비밀번호와 동일합니다."),
    _CERTIFIED_KEY_EXPIRED(HttpStatus.BAD_REQUEST, "ACCOUNT400", "입력 가능한 시간이 초과되었습니다."),
    _INVALID_CERTIFIED_KEY(HttpStatus.BAD_REQUEST, "ACCOUNT400", "인증 번호가 일치하지 않습니다."),

    // 일반 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증되지 않은 요청입니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "접근 권한이 없습니다."),
    _UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "알 수 없는 오류가 발생했습니다."),

    _FILE_DOWNLOAD_FAILED(HttpStatus.BAD_REQUEST, "FILE404", "파일을 다운받을 수 없습니다."),
    _PROMPT_LOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "PROMPT500", "프롬프트 파일을 로드하던 중 오류가 발생했습니다."),
    _EMAIL_SEND_FAIL(HttpStatus.BAD_REQUEST, "EMAIL400", "이메일 전송에 실패했습니다."),

    _JWT_NOT_FOUND(HttpStatus.NOT_FOUND, "JWT404", "토큰을 찾을 수 없습니다"),
    _JWT_INVALID(HttpStatus.UNAUTHORIZED, "JWT400", "유효하지 않는 토큰입니다"),
    _JWT_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT400", "만료된 토큰입니다"),
    _JWT_BLACKLIST(HttpStatus.UNAUTHORIZED, "JWT400", "접근 불가능한 토큰입니다"),
    _JWT_UNKNOWN_ERROR(HttpStatus.UNAUTHORIZED, "JWT400", "JWT 인증 중 알 수 없는 오류가 발생했습니다."),

    // Diary
    _DUPLICATE_DIARY_DATE(HttpStatus.BAD_REQUEST, "DIARY400", "일기는 하루에 한 개만 작성할 수 있습니다."),
    _INVALID_PATCH_DIARY(HttpStatus.BAD_REQUEST, "DIARY400", "당일 작성한 일기만 수정할 수 있습니다."),
    _INVALID_DIARY_CONTENT_LENGTH(HttpStatus.BAD_REQUEST, "DIARY400", "일기의 내용은 500자 이하여야 합니다."),
    _USER_FORBIDDEN_DIARY(HttpStatus.FORBIDDEN, "DIARY403", "다이어리에 접근 권한이 없습니다."),
    _DIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "DIARY404", "해당 다이어리를 찾을 수 없습니다."),

    // Emotion
    _INVALID_EMOTION_SCORE(HttpStatus.BAD_REQUEST, "EMOTION400", "감정 점수는 -1과 1 사이여야 합니다."),
    _INVALID_EMOTION_SPECTRUN(HttpStatus.BAD_REQUEST, "EMOTION400", "유효하지 않은 감정 지표입니다."),
    _ANALYZE_EMOTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "EMOTION500", "감정 분석 API 호출 중 오류가 발생했습니다.")
    ;



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDto getReason() {
        return ErrorReasonDto.builder()
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }

    @Override
    public ErrorReasonDto getReasonHttpStatus() {
        return ErrorReasonDto.builder()
                .httpStatus(httpStatus)
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }

}
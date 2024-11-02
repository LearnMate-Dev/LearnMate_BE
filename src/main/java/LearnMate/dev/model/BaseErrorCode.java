package LearnMate.dev.model;

import LearnMate.dev.model.dto.ErrorReasonDto;

public interface BaseErrorCode {

    public ErrorReasonDto getReason();

    public ErrorReasonDto getReasonHttpStatus();

}
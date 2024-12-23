package LearnMate.dev.common.exception;

import LearnMate.dev.common.status.ErrorStatus;
import LearnMate.dev.model.dto.ErrorReasonDto;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{

    private final ErrorStatus errorStatus;

    public ApiException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }

    public ErrorReasonDto getErrorReason() {
        return this.errorStatus.getReason();
    }

    public ErrorReasonDto getErrorReasonHttpStatus() {
        return this.errorStatus.getReasonHttpStatus();
    }

}

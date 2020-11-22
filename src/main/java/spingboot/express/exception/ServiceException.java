package spingboot.express.exception;

import org.apache.commons.lang3.StringUtils;
import spingboot.express.enums.ErrorCode;

public class ServiceException extends RuntimeException{

    private ErrorCode errorCode;
    private String extraMessage;

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorCode errorCode, String extraMessage) {
        super(errorCode.getErrorMessage());
        this.extraMessage = extraMessage;
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorCode errorCode, Throwable rootCause) {
        super(errorCode.getErrorMessage(), rootCause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode.getErrorCode();
    }

    public String getExtraMessage() {
        return this.extraMessage;
    }

    public String getFullMessage() {
        if (StringUtils.isBlank(this.extraMessage)) {
            return this.getMessage();
        } else {
            return this.getMessage() + ", (" + this.getExtraMessage() + ").";
        }
    }
}

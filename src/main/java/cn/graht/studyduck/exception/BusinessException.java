package cn.graht.studyduck.exception;

import cn.graht.studyduck.commons.ErrorCode;

/**
 *  自定义异常
 * @author graht
 */
public class BusinessException extends RuntimeException{
    private final String description;
    private final int code;

    public BusinessException(String message,String description, int code) {
        super(message);
        this.description = description;
        this.code = code;
    }
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.description = errorCode.getDescription();
        this.code = errorCode.getCode();
    }
    public BusinessException(ErrorCode errorCode,String description) {
        super(errorCode.getMsg());
        this.description = description;
        this.code = errorCode.getCode();
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }
}

package cn.graht.springbootinit.exception;

import cn.graht.springbootinit.commons.ErrorCode;
import cn.graht.springbootinit.commons.ResultApi;
import cn.graht.springbootinit.commons.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BusinessExceptionAdviser {
    @ExceptionHandler(BusinessException.class)
    public ResultApi handleBusinessException(BusinessException e) {
        log.error("[BusinessException] {} ==<{}>", e.getMessage(),e.getDescription(),e);
        return ResultUtil.error(e.getCode(),e.getMessage(),e.getDescription());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResultApi handleRuntimeException(RuntimeException e) {
        log.error("[RuntimeException] {}",e.getMessage(), e);
        return ResultUtil.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
    }
}

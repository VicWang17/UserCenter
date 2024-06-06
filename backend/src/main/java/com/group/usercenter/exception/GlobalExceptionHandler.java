package com.group.usercenter.exception;

import com.group.usercenter.common.BaseResponse;
import com.group.usercenter.common.ErrorCode;
import com.group.usercenter.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse BusinessExceptionHandler(BusinessException e){
        log.error("businessException:" + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());

    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e){
        log.error("runtimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
    }
}

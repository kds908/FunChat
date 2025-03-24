package cn.abner.funchat.exception;

import cn.abner.funchat.common.ResponseResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Description for this class
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2025/3/19
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BizException.class)
    public ResponseResult<Object> runtimeException(BizException e) {
        return ResponseResult.fail(e.getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseResult<Object> noResourceFoundException(NoResourceFoundException e) {
        return ResponseResult.notFound(e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseResult<Object> noHandlerFoundException(NoHandlerFoundException e) {
        return ResponseResult.notFound(e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult<Object> httpRequestMethodNotSupportedException(NoHandlerFoundException e) {
        return ResponseResult.methodNotSupport(e.getMessage());
    }
}

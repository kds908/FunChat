package cn.abner.funchat.common;

import cn.abner.funchat.entity.enums.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description for this class
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2025/3/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> {
    private int code;
    private String status;
    private String msg;
    private T data;

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getStatus(), ResultCodeEnum.SUCCESS.getMessage(), data);
    }

    public static <T> ResponseResult<T> fail(String msg) {
        return new ResponseResult<>(ResultCodeEnum.ERROR.getCode(), ResultCodeEnum.ERROR.getStatus(), msg, null);
    }

    public static <T> ResponseResult<T> notFound(String msg) {
        return new ResponseResult<>(ResultCodeEnum.NOT_FOUND.getCode(), ResultCodeEnum.NOT_FOUND.getStatus(), msg, null);
    }

    public static <T> ResponseResult<T> methodNotSupport(String msg) {
        return new ResponseResult<>(ResultCodeEnum.METHOD_NOT_SUPPORT.getCode(), ResultCodeEnum.METHOD_NOT_SUPPORT.getStatus(), msg, null);
    }

    public static <T> ResponseResult<T> forbidden() {
        return new ResponseResult<T>(ResultCodeEnum.FORBIDDEN.getCode(), ResultCodeEnum.FORBIDDEN.getStatus(), ResultCodeEnum.FORBIDDEN.getMessage(), null);
    }

    public static <T> ResponseResult<T> unauthorized() {
        return new ResponseResult<T>(ResultCodeEnum.UNAUTHORIZED.getCode(), ResultCodeEnum.UNAUTHORIZED.getStatus(), ResultCodeEnum.UNAUTHORIZED.getMessage(), null);
    }
}

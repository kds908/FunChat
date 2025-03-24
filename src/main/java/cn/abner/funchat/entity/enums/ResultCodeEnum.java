package cn.abner.funchat.entity.enums;

import lombok.Getter;

/**
 * Description for this class
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2025/3/19
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS(200, "success", "成功"),
    ERROR(500, "error", "服务异常"),
    UNAUTHORIZED(401, "unauthorized", "未授权的请求"),
    FORBIDDEN(403, "forbidden", "禁止的请求"),
    NOT_FOUND(404, "not found", "未知的请求"),
    METHOD_NOT_SUPPORT(405, "method not support", "请求方式错误");

    private final Integer code;
    private final String status;
    private final String message;

    ResultCodeEnum(Integer code,String status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}

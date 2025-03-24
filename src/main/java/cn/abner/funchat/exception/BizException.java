package cn.abner.funchat.exception;

/**
 * Description for this class
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2025/3/24
 */
public class BizException extends RuntimeException {
    public BizException(String message) {
        super(message);
    }
}

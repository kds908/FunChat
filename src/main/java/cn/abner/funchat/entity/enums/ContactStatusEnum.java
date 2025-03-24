package cn.abner.funchat.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description for this class
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2025/3/25
 */
@Getter
@AllArgsConstructor
public enum ContactStatusEnum {
    STRANGER(0),
    FRIEND(1),
    DELETE(2),
    BE_DELETED(3),
    BLACK(4),
    BE_BLACK(5),;

    private final Integer status;
}

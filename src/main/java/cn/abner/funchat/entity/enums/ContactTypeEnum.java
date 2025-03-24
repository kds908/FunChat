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
public enum ContactTypeEnum {
    FRIEND(0),
    GROUP(1);
    private final Integer type;
}

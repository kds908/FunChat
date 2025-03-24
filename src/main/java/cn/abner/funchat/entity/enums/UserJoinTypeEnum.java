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
 * @date: 2025/3/24
 */
@Getter
@AllArgsConstructor
public enum UserJoinTypeEnum {
    AUTO(0),
    AGREE(1);
    private final Integer type;
}

package cn.abner.funchat.entity.dto;

import cn.abner.funchat.annotation.FieldIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Description for this class
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2025/3/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTokenDTO implements Serializable {
    @Serial
    @FieldIgnore
    private static final long serialVersionUID = 1L;

    private String token;
    private String uid;
    private String username;
    private Boolean admin;
}

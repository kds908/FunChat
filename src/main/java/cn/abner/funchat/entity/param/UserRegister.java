package cn.abner.funchat.entity.param;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * Description for this class
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2025/3/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegister {
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, max = 16, message = "密码长度6-16位")
    private String password;
    @Length(max = 8, message = "用户昵称不能超过8个字")
    private String nickname;
    @Email(message = "无效的 email 格式")
    private String email;
    @NotEmpty
    private String identity;
    @NotEmpty
    private String captchaCode;
}

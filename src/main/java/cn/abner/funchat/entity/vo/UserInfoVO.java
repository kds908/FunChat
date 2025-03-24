package cn.abner.funchat.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
public class UserInfoVO {
    private String uid;
    private String username;
    private String nickname;
    private String email;
    private Integer gender;
    private String password;
    private Integer joinType;
    private String personalSignature;
    private String avatar;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-8")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-8")
    private LocalDateTime lastLoginTime;
    private Long lastOffTime;
    private String areaName;
    private String areaCode;

    private String token;
    private Boolean admin;
    private Integer contactStatus;
}

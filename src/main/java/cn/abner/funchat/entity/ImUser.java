package cn.abner.funchat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

/**
 * User Info
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2025/3/19
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@DynamicInsert
@DynamicUpdate // 不更新 null 字段
@Table(name = "im_user")
public class ImUser {
    @Id
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
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-8")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-8")
    private LocalDateTime lastLoginTime;
    private Long lastOffTime;
    private String areaName;
    private String areaCode;
}

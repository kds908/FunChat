package cn.abner.funchat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.*;

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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "im_contact")
@IdClass(ContactKey.class)
public class ImContact {
    @Id
    private String userId;
    @Id
    private String contactId;
    private Integer contactType;
    private LocalDateTime createTime;
    private Integer status;
    private Long lastUpdateTime;
}

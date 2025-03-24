package cn.abner.funchat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "im_group")
public class ImGroup {
    @Id
    private String groupId;
    private String groupName;
    private String groupOwnerId;
    private LocalDateTime createTime;
    private String groupNotice;
    private Integer joinType;
    private Integer status;
}

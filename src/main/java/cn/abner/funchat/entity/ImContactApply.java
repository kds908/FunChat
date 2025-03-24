package cn.abner.funchat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Entity
@Table(name = "im_contact_apply")
public class ImContactApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applyId;
    private String applyUserId;
    private String receiveUserId;
    private Integer contactType;
    private String contactId;
    private Long lastApplyTime;
    private Integer status;
    private String applyInfo;
}

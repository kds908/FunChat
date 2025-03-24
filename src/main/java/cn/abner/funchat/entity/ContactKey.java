package cn.abner.funchat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class ContactKey implements Serializable {
    private String userId;
    private String contactId;
}

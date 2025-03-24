package cn.abner.funchat.entity.param;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
public class GroupEdit {
    private String groupId;
    @NotEmpty
    private String groupName;
    private String groupNotice;
    @NotNull
    private Integer joinType;
}

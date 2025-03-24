package cn.abner.funchat.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
@ConfigurationProperties(prefix = "system.settings")
public class SystemSettings {
    private Integer maxGroupCount;
    private Integer maxGroupMemberCount;
    private Integer maxImageSize;
    private Integer maxVideoSize;
    private Integer maxFileSize;
    private String robotUid;
    private String robotNickname;
    private String robotWelcome;
}

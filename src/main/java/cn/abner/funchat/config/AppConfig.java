package cn.abner.funchat.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description for this class
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2025/3/25
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "app.settings")
public class AppConfig {
    private String projectFolder;
}

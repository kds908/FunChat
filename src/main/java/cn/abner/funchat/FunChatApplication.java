package cn.abner.funchat;

import cn.abner.funchat.config.AppConfig;
import cn.abner.funchat.config.SystemSettings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({SystemSettings.class, AppConfig.class})
public class FunChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(FunChatApplication.class, args);
    }

}

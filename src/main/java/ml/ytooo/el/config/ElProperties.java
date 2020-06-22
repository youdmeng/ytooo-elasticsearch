package ml.ytooo.el.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * el端口地址配置
 * Created by Youdmeng on 2019/6/27 0027.
 */
@Data
@Component
@ConfigurationProperties(prefix = "elasticsearch")
public class ElProperties {

    private String host;

    private Integer port;
}

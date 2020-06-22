package ml.ytooo.el.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * el端口地址配置
 * Created by Youdmeng on 2019/6/27 0027.
 */
@Component
@ConfigurationProperties(prefix = "elasticsearch")
public class ElProperties {

    private String host;

    private Integer port;

    /**
     * 属性get
     */
    public String getHost() {
        return host;
    }

    /**
     * 属性set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * 属性get
     */
    public Integer getPort() {
        return port;
    }

    /**
     * 属性set
     */
    public void setPort(Integer port) {
        this.port = port;
    }
}

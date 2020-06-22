package ml.ytooo.el.client;

import java.util.Objects;

import ml.ytooo.el.config.ElProperties;
import com.google.gson.GsonBuilder;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * el客户端
 * Created by Youdmeng on 2019/6/27 0027.
 **/
@Component
public class EsJestClient {
//    /**
//     * clusterName
//     */
//    @Value(value = "${clusterName}")
//    private String clusterName;


    @Autowired
    private ElProperties properties;
    private static JestClient client;

    private static EsJestClient esJestClient;

    @PostConstruct
    private void init() {
        esJestClient = this;
    }
    /**
     * 获取客户端
     *
     * @return jestclient
     */
    public static synchronized JestClient getClient() {
        if (client == null) {
            build();
        }
        return client;
    }

    /**
     * 关闭客户端
     */
    public static void close(JestClient client) {
        if (!Objects.isNull(client)) {
            try {
                client.shutdownClient();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 建立连接
     */
    private static void build() {
        String url = esJestClient.properties.getHost() + ":" + esJestClient.properties.getPort();
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(
                new HttpClientConfig
                        .Builder(url)
                        .multiThreaded(true)
                        //一个route 默认不超过2个连接  路由是指连接到某个远程注解的个数。总连接数=route个数 * defaultMaxTotalConnectionPerRoute
                        .defaultMaxTotalConnectionPerRoute(2)
                        //所有route连接总数
                        .maxTotalConnection(2)
                        .connTimeout(10000)
                        .readTimeout(10000)
                        .gson(new GsonBuilder()
                                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                                .create())
                        .build()
        );
        client = factory.getObject();
    }
}

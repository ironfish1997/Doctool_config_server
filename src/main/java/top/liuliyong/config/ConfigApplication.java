package top.liuliyong.config;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigServer
@RestController
public class ConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }

    /**
     * 远程gitwebhooks调用此接口由此接口发起 http post 请求去
     * 触发bus-refresh接口，通过rabbitmq等一系列默认处理机制就可以实现动态刷新机制
     */
    @PostMapping("/refresh")
    public void refresh() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "http://localhost:8080/actuator/bus-refresh";
        HttpPost httpPost = new HttpPost(url);
        // 执行请求
        try {
            httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

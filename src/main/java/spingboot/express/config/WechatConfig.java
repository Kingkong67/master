package spingboot.express.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class WechatConfig {

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.secret}")
    private String secret;

    @Value("${wechat.requestUrl}")
    private String requestUrl;
}

package spingboot.express.dto;

import lombok.Data;
import org.springframework.context.annotation.PropertySource;

@Data
@PropertySource("application-wechat.properties")
public class WechatAuthRequest {

    /**
     * 小程序 appId
     */
    private String appId;

    /**
     * 小程序 appSecret
     */
    private String secret;

    /**
     * 登录时获取的 code
     */
    private String userCode;

    private String grant_type;

    public WechatAuthRequest() {}

}

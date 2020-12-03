package spingboot.express.controller.wechatController;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import spingboot.express.commons.WechatAppResult;
import spingboot.express.config.WechatConfig;
import spingboot.express.dto.WechatAuthRequest;
import spingboot.express.enums.UserCommonStatus;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wechat")
@Slf4j
public class WeChatLoginController {

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/getToken")
    public WechatAppResult getLoginInformation(@RequestBody WechatAuthRequest wechatAuthRequest) {
        WechatAppResult wechatAppResult = new WechatAppResult();
        String userCode = wechatAuthRequest.getUserCode();
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("appId", wechatConfig.getAppId());
        parameterMap.put("secret", wechatConfig.getSecret());
        parameterMap.put("userCode", userCode);
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={appId}&secret={secret}&js_code={userCode}&grant_type=authorization_code";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, parameterMap);
        JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
        log.info("the body is {}.", jsonObject);
        wechatAppResult.setOpenId(jsonObject.getString("openid"));
        wechatAppResult.setSession_key(jsonObject.getString("session_key"));
        wechatAppResult.setCode(UserCommonStatus.SUCCESS.getCode());
        wechatAppResult.setIsSuccess(true);
        wechatAppResult.setMessage(UserCommonStatus.SUCCESS.getMessage());
        return wechatAppResult;
    }
}

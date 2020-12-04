package spingboot.express.service.Impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spingboot.express.config.WechatConfig;
import spingboot.express.constant.Constants;
import spingboot.express.constant.PlatformKey;
import spingboot.express.dto.WechatAuthRequest;
import spingboot.express.dto.WechatAuthResult;
import spingboot.express.enums.ErrorCode;
import spingboot.express.service.AccessTokenService;
import spingboot.express.service.AuthenticationService;
import spingboot.express.service.RedisCacheService;

import java.util.HashMap;
import java.util.Map;

/**
 * 授权service具体实现类
 */
@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private RedisCacheService redisCacheService;

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public WechatAuthResult getTokenByUserCode(WechatAuthRequest wechatAuthRequest) {
        WechatAuthResult wechatAuthResult = new WechatAuthResult();
        String userCode = wechatAuthRequest.getUserCode();
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put(Constants.APP_ID, wechatConfig.getAppId());
        parameterMap.put(Constants.SECRET, wechatConfig.getSecret());
        parameterMap.put(Constants.USER_CODE, userCode);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(wechatConfig.getRequestUrl(), String.class, parameterMap);
        JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
        if (jsonObject == null) {
            wechatAuthResult.setErrCode(ErrorCode.GET_TOKEN_VALUE_FAILED.getErrorCode());
            wechatAuthResult.setErrMsg(ErrorCode.GET_TOKEN_VALUE_FAILED.getErrorMessage());
        } else {
            log.info("the body is {}.", jsonObject);
            String openId = jsonObject.getString(Constants.OPEN_ID);
            String sessionKey = jsonObject.getString(Constants.SESSION_KEY);
            String token = accessTokenService.createAccessTokenByUserCode(PlatformKey.WECHAT, userCode);
            log.info("The openId => {}, sessionKey => {}, token => {}.", openId, sessionKey, token);
            wechatAuthResult.setTokenId(token);
            redisCacheService.saveObject(Constants.AUTH_USER_CODE + Constants.SEPARATOR + userCode, token);
        }
        return wechatAuthResult;

    }
}

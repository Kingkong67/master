package spingboot.express.service;


import spingboot.express.dto.WechatAuthRequest;
import spingboot.express.dto.WechatAuthResult;

/**
 * 授权service
 */

public interface AuthenticationService {

    /**
     * 获取用户的请求token值
     * @param wechatAuthRequest 请求参数
     */
    WechatAuthResult getTokenByUserCode(WechatAuthRequest wechatAuthRequest);

}

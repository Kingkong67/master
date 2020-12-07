package spingboot.express.controller.authController;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spingboot.express.commons.Result;
import spingboot.express.constant.PlatformKey;
import spingboot.express.dto.RefreshTokenDto;
import spingboot.express.dto.WechatAuthRequest;
import spingboot.express.dto.WechatAuthResult;
import spingboot.express.dto.auth.AuthRequest;
import spingboot.express.dto.auth.AuthResult;
import spingboot.express.enums.UserCommonStatus;
import spingboot.express.facade.LoginFacade;
import spingboot.express.service.AuthenticationService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private LoginFacade loginFacade;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/getToken")
    public Result getLoginInformation(@RequestBody WechatAuthRequest wechatAuthRequest) {
        log.info("【微信端获取token值】-------- 开始");
        WechatAuthResult wechatAuthResult = authenticationService.getTokenByUserCode(wechatAuthRequest);
        Result result = new Result();
        if (wechatAuthResult != null) {
            log.info("the wechatResult info is => {}.", wechatAuthResult);
            result.setData(wechatAuthResult);
            result.setCode(UserCommonStatus.SUCCESS.getCode());
            result.setMessage(UserCommonStatus.SUCCESS.getMessage());
            result.setIsSuccess(true);
        } else {
            log.error("get wechatAuthResult failed. do not get information.");
            result.setData(null);
            result.setCode(UserCommonStatus.FAIL.getCode());
            result.setMessage(UserCommonStatus.FAIL.getMessage());
            result.setIsSuccess(false);
        }
        return result;
    }


    @PostMapping("/signIn")
    public AuthResult signIn(HttpServletRequest request, @RequestBody AuthRequest authRequest) {
        resolveRequestParameter(authRequest, PlatformKey.WEBSITE);
        log.info("【用户登录授权开始】");
        return loginFacade.signIn(request, authRequest);
    }

    private void resolveRequestParameter(AuthRequest authRequest, PlatformKey platformKey) {
        if (authRequest.getPlatformKey() == null) {
            authRequest.setPlatformKey(platformKey);
        }
    }

    @PostMapping("/refreshToken")
    public AuthResult refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        AuthResult authResult = new AuthResult();
        try {
            String newToken = loginFacade.refreshToken(refreshTokenDto);
            authResult.setRefreshToken(newToken);
        } catch (Exception e) {
            log.error("get refresh token for {} failed.", authResult.getPlatformKey());
            authResult.setRefreshToken(null);
        }
        return authResult;
    }

}

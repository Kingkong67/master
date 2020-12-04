package spingboot.express.controller.wechatController;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spingboot.express.commons.Result;
import spingboot.express.dto.WechatAuthRequest;
import spingboot.express.dto.WechatAuthResult;
import spingboot.express.enums.UserCommonStatus;
import spingboot.express.service.AuthenticationService;

@RestController
@RequestMapping("/wechat")
@Slf4j
public class WeChatLoginController {

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
}

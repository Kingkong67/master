package spingboot.express.controller.userController;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spingboot.express.commons.Result;
import spingboot.express.commons.UserCommonStatus;
import spingboot.express.pojo.User;
import spingboot.express.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户模块控制层
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 用户密码登录接口
     *
     * @param paramMap 传入参数存储集合
     * @return 返回Result对象
     */
    @PostMapping(value = "/loginUserWithPwd")
    public Result loginUser(@RequestBody HashMap<String, Object> paramMap) {
        Result result = new Result();
        log.info("[用户密码登录]开始------");
        try {
            User user = userService.loginUserWithPwd(paramMap);
            //SessionHandler.save(user, httpSession);
            if (user != null) {
                result.setIsSuccess(true);
                result.setCode(UserCommonStatus.getCodeByName("SUCCESS"));
                result.setMessage("用户密码登录成功！");
                result.setData(userService.getAllInfo(paramMap));
                log.info("[用户密码登录] => loginUser success!");
                return result;
            } else {
                log.warn("[用户密码登录失败] => loginUser failed!");
                result.setCode(UserCommonStatus.getCodeByName("NOT_EXIST"));
                result.setMessage("用户手机号码不存在");
                return result;
            }
        } catch (Exception e) {
            log.error("[用户密码登录] => loginUser failed!" + e);
            result.setCode(UserCommonStatus.getCodeByName("ERROR"));
            result.setIsSuccess(false);
            result.setMessage("用户密码登录失败");
            return result;
        }
    }

    /**
     * 用户短信验证码登录接口（手机号+短信验证码登录）
     *
     * @param paramMap 传入储存参数集合
     * @return 返回Result对象
     */
    @PostMapping(value = "/loginUserWithCode")
    public Result loginWithCode(@RequestBody HashMap<String, Object> paramMap) {
        Result result = new Result();
        try {
            User user = userService.loginWithCode(paramMap);
            //SessionHandler.save(user, httpSession);
            result.setIsSuccess(true);
            result.setData(userService.getAllInfo(paramMap));
            result.setCode(UserCommonStatus.getCodeByName("SUCCESS"));
            result.setMessage("用户短信验证码登录成功！");
            log.info("[用户短信验证码登录] => loginWithCode success!");
            return result;
        } catch (Exception e) {
            result.setIsSuccess(false);
            result.setCode(UserCommonStatus.getCodeByName("ERROR"));
            result.setMessage("用户短信验证码登录失败！");
            log.error("[用户短信验证码登录] => loginWithCode failed!", e);
            return result;
        }

    }

}

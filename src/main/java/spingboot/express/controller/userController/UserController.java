package spingboot.express.controller.userController;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import spingboot.express.commons.Result;
import spingboot.express.dto.MobilePhoneCodeDto;
import spingboot.express.dto.UserInfoDto;
import spingboot.express.enums.ErrorCode;
import spingboot.express.enums.UserCommonStatus;
import spingboot.express.pojo.User;
import spingboot.express.service.UserService;

/**
 * 用户模块控制层
 */
@RestController
@RequestMapping("/userInfo")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @Value("${server.port}")
    private int port;

    @GetMapping(value = "/hello")
    public String hello1() {
        try {
            System.out.println(port);
            UserInfoDto userInfoDto = new UserInfoDto();
            userInfoDto.setId(1L);
            return userService.getBasicUser(userInfoDto).getNickname().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "-111111";
        }
    }

    /**
     * 用户密码登录接口
     *
     * @param userInfoDto 传入参数存储集合
     * @return 返回Result对象
     */
    @PostMapping(value = "/loginUserWithPwd")
    public Result loginUser(@RequestBody UserInfoDto userInfoDto) {
        Result result = new Result();
        log.info("[用户密码登录]开始------");
        try {
            User user = userService.loginUserWithPwd(userInfoDto);
            //SessionHandler.save(user, httpSession);
            if (user != null) {
                result.setIsSuccess(true);
                result.setCode(UserCommonStatus.SUCCESS.getCode());
                result.setMessage(UserCommonStatus.SUCCESS.getMessage());
                result.setData(userService.getUserBasicInfo(user));
                log.info("[用户密码登录] => loginUser success!");
                return result;
            } else {
                log.error("[用户密码登录失败] => loginUser failed!");
                result.setCode(ErrorCode.MOBILE_PHONE_INVALID.getErrorCode());
                result.setMessage(ErrorCode.MOBILE_PHONE_INVALID.getErrorMessage());
                return result;
            }
        } catch (Exception e) {
            log.error("[用户密码登录] => loginUser failed!" + e);
            result.setCode(ErrorCode.USER_SIGN_IN_FAILED.getErrorCode());
            result.setIsSuccess(false);
            result.setMessage(ErrorCode.USER_SIGN_IN_FAILED.getErrorMessage());
            return result;
        }
    }

    /**
     * 用户短信验证码登录接口（手机号+短信验证码登录）
     *
     * @param mobilePhoneCodeDto 传入储存参数集合
     * @return 返回Result对象
     */
    @PostMapping(value = "/loginUserWithCode")
    public Result loginWithCode(@RequestBody MobilePhoneCodeDto mobilePhoneCodeDto) {
        Result result = new Result();
        try {
            User user = userService.loginWithCode(mobilePhoneCodeDto);
            result.setIsSuccess(true);
            result.setData(userService.getUserBasicInfo(user));
            result.setCode(UserCommonStatus.SUCCESS.getCode());
            result.setMessage(UserCommonStatus.SUCCESS.getMessage());
            log.info("[用户短信验证码登录] => loginWithCode success!");
            return result;
        } catch (Exception e) {
            result.setIsSuccess(false);
            result.setCode(ErrorCode.USER_SIGN_IN_FAILED.getErrorCode());
            result.setMessage(ErrorCode.USER_SIGN_IN_FAILED.getErrorMessage());
            log.error("[用户短信验证码登录] => loginWithCode failed, the exception is {}.", e.getMessage());
            return result;
        }

    }
    /**
     * 用户忘记密码登录接口（使用验证码登录）
     *
     * @param mobilePhoneCodeDto 传入储存参数集合
     * @return 返回Result对象
     */
    @PostMapping(value = "/forgetUserPwd")
    public Result forgetPwd(@RequestBody MobilePhoneCodeDto mobilePhoneCodeDto) {
        Result result = new Result();
        log.info("[用户忘记密码登录]开始-----");
        try {
            User user = userService.loginWithCode(mobilePhoneCodeDto);
            log.info("[用户忘记密码登录] forgetPwd success!");
            result.setIsSuccess(true);
//            result.setData(userService.getAllInfo());
            result.setCode(UserCommonStatus.SUCCESS.getCode());
            result.setMessage(UserCommonStatus.SUCCESS.getMessage());
            return result;
        } catch (Exception e) {
            log.info("[用户忘记密码登录] forgetPwd failed!", e);
            result.setIsSuccess(false);
            result.setCode(ErrorCode.USER_SIGN_IN_FAILED.getErrorCode());
            result.setMessage(ErrorCode.USER_SIGN_IN_FAILED.getErrorMessage());
            return result;
        }
    }
    /**
     * 用户重置密码接口
     *
     * @param userInfoDto 传入储存参数集合
     * @return 返回Result对象
     */
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    public Result resetPwd(@RequestBody UserInfoDto userInfoDto) {
        Result result = new Result();
        log.info("[用户重置密码]开始-----");
        try {
            userService.resetPwd(userInfoDto);
            log.info("[用户重置密码] resetPwd success!");
            result.setMessage(UserCommonStatus.SUCCESS.getMessage());
            result.setIsSuccess(true);
            result.setCode(UserCommonStatus.SUCCESS.getCode());
            return result;
        } catch (Exception e) {
            log.error("[用户重置密码] resetPwd failed!", e);
            result.setMessage(ErrorCode.PASSWORD_RESET_FAILED.getErrorMessage());
            result.setIsSuccess(false);
            result.setCode(ErrorCode.PASSWORD_RESET_FAILED.getErrorCode());
            return result;
        }
    }
    /**
     * 用户获取验证码接口
     *
     * @param mobilePhoneCodeDto 传入储存参数集合
     * @return 返回Result对象
     */
    @PostMapping(value = "/getCode")
    public Result getCode(@RequestBody MobilePhoneCodeDto mobilePhoneCodeDto) {
        Result result = new Result();
        log.info("[用户获取验证码]开始-----");
        try {
            log.info("[用户获取验证码] getCode success!");
            result.setMessage(UserCommonStatus.SUCCESS.getMessage());
            result.setData(userService.getCode(mobilePhoneCodeDto));
            result.setIsSuccess(true);
            result.setCode(UserCommonStatus.SUCCESS.getCode());
            return result;
        } catch (Exception e) {
            log.error("[用户获取验证码] getCode failed!", e);
            result.setMessage(ErrorCode.GET_VALID_CODE_FAILED.getErrorMessage());
            result.setIsSuccess(false);
            result.setCode(ErrorCode.GET_VALID_CODE_FAILED.getErrorCode());
            return result;
        }
    }
    /**
     * 用户注册接口
     *
     * @param userInfoDto 传入储存参数集合
     * @return 返回Result对象
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public Result addUser(@RequestBody UserInfoDto userInfoDto) {
        Result result = new Result();
        log.info("%%%%%%%%% User sign up start %%%%%%%%%");
        try {
            if (userService.addUser(userInfoDto) == UserCommonStatus.SUCCESS.getCode()) {
                result.setIsSuccess(true);
                result.setCode(UserCommonStatus.SUCCESS.getCode());
                result.setData(userService.getAllInfo(userInfoDto));
                result.setMessage(UserCommonStatus.SUCCESS.getMessage());
                log.info("User signs up success!");
            } else if (userService.addUser(userInfoDto) == UserCommonStatus.ERROR.getCode()) {
                result.setIsSuccess(false);
                result.setCode(ErrorCode.MOBILE_PHONE_USED_BY_OTHERS.getErrorCode());
                result.setMessage(ErrorCode.MOBILE_PHONE_USED_BY_OTHERS.getErrorMessage());
                log.error("The Mobile Phone Number {} is sign up by Others.", userInfoDto.getTelephone());
            }
            return result;
        } catch (Exception e) {
            result.setIsSuccess(false);
            result.setCode(ErrorCode.USER_SIGN_UP_FAILED.getErrorCode());
            result.setMessage(ErrorCode.USER_SIGN_UP_FAILED.getErrorMessage());
            log.error("User signs up failed, the exception is = {}.", e.getMessage());
            return result;
        }
    }

    /**
     * 用户注销接口
     *
     * @param userInfoDto 传入存储参数集合
     * @return 返回Result对象
     */
    @PostMapping(value = "/deleteUser")
    public Result deleteUser(@RequestBody UserInfoDto userInfoDto) {
        Result result = new Result();
        try {
            userService.deleteUser(userInfoDto);
            log.info("[用户注销] => deleteUser success!");
            result.setCode(UserCommonStatus.SUCCESS.getCode());
            result.setIsSuccess(true);
            result.setMessage(UserCommonStatus.SUCCESS.getMessage());
            return result;
        } catch (Exception e) {
            log.error("[用户注销] => deleteUser failed!", e);
            result.setCode(ErrorCode.USER_DISABLED_FAILED.getErrorCode());
            result.setIsSuccess(false);
            result.setMessage(ErrorCode.USER_DISABLED_FAILED.getErrorMessage());
            return result;
        }
    }
    /**
     * 获取用户信息接口
     *
     * @param userInfoDto 传入存储结果参数集合
     * @return 返回Result类型
     */
    @PostMapping(value = "/getBasicInfo")
    public Result getBasicUser(@RequestBody UserInfoDto userInfoDto) {
        Result result = new Result();
        log.info("[用户信息获取] 开始-----");
        try {
            UserInfoDto userInfo = userService.getBasicUser(userInfoDto);
            result.setCode(UserCommonStatus.SUCCESS.getCode());
            log.info("[用户信息获取] getBasicUser success!");
            result.setIsSuccess(true);
            result.setMessage(UserCommonStatus.SUCCESS.getMessage());
            result.setData(userInfo);
            return result;
        } catch (Exception e) {
            result.setCode(ErrorCode.USER_INFO_NOTFOUND.getErrorCode());
            log.error("[用户信息获取] getBasicUser failed!", e);
            result.setIsSuccess(false);
            result.setMessage(ErrorCode.USER_INFO_NOTFOUND.getErrorMessage());
            return result;
        }
    }

    /**
     * 用户实名制接口
     *
     * @param userInfoDto 传入存储参数集合
     * @return 返回Result接口
     */
    @RequestMapping(value = "/realUser", method = RequestMethod.POST)
    public Result realUser(@RequestBody UserInfoDto userInfoDto) {
        Result result = new Result();
        log.info("【用户实名制】 开始-----");
        try {
            userService.realUser(userInfoDto);
            result.setIsSuccess(true);
            result.setCode(UserCommonStatus.SUCCESS.getCode());
            result.setMessage(UserCommonStatus.SUCCESS.getMessage());
            log.info("[用户实名制] => realUser success！");
            return result;
        } catch (Exception e) {
            result.setIsSuccess(false);
            log.error("[用户实名制] => realUser failed!", e);
            result.setCode(ErrorCode.USER_IDENTITY_FAILED.getErrorCode());
            result.setMessage(ErrorCode.USER_IDENTITY_FAILED.getErrorMessage());
            return result;
        }
    }
}

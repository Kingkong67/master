package spingboot.express.service;

/**
 * 用户模块的业务层接口
 */


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import spingboot.express.commons.Result;
import spingboot.express.controller.orderController.OrderController;
import spingboot.express.dto.MobilePhoneCodeDto;
import spingboot.express.dto.UserInfoDto;
import spingboot.express.enums.OrderTypeEnum;
import spingboot.express.pojo.User;

import java.util.HashMap;
import java.util.Map;

public interface UserService {

    User loginUserWithPwd(UserInfoDto userInfoDto) throws Exception;

    int addUser(UserInfoDto userInfoDto) throws Exception;

    User loginWithCode(MobilePhoneCodeDto mobilePhoneCodeDto) throws Exception;

    int deleteUser(UserInfoDto userInfoDto) throws Exception;

    UserInfoDto getBasicUser(UserInfoDto userInfoDto) throws Exception;

    void realUser(UserInfoDto userInfoDto) throws Exception;

    String getCode(MobilePhoneCodeDto mobilePhoneCodeDto) throws Exception;

    User viewIfFullUserInformation(UserInfoDto userInfoDto) throws Exception;

    int resetPwd(UserInfoDto userInfoDto) throws Exception;

    User getAllInfo(UserInfoDto userInfoDto) throws Exception;

    @Deprecated
    String getAddress(UserInfoDto userInfoDto) throws Exception;

    /**
     * 获取用户基本信息
     * @param user user
     * @return 返回用户基本信息
     */
    User getUserBasicInfo(User user) throws Exception;


}


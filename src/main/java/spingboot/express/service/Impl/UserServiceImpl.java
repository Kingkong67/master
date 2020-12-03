package spingboot.express.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spingboot.express.commons.Result;
import spingboot.express.dto.MobilePhoneCodeDto;
import spingboot.express.dto.ReceiveOrderDto;
import spingboot.express.dto.UserInfoDto;
import spingboot.express.enums.ErrorCode;
import spingboot.express.enums.UserCommonStatus;
import spingboot.express.exception.ServiceException;
import spingboot.express.mapper.UserMapper;
import spingboot.express.pojo.User;
import spingboot.express.service.UserService;
import spingboot.express.utils.ImageUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    /**
     * 日志管理工具
     */
    protected static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

//    @Autowired
//    private RedisCacheService redisCacheService;

    /**
     * 用户注册业务方法实现
     *
     * @param userInfoDto 传入储存结果参数
     * @return 返回注册状态
     * @throws Exception 抛出异常
     */
    @Override
    public int addUser(UserInfoDto userInfoDto) throws Exception {
        User user = new User();
        user.setNickname(userInfoDto.getNickname());
        user.setTelephone(userInfoDto.getTelephone());
        user.setPassword(userInfoDto.getPassword());
        user.setSex(userInfoDto.getSex());
        long currentTime = System.currentTimeMillis();
//        try {
//            redisCacheService.saveObject("user", user);
//        } catch (Exception e) {
//            logger.error("【数据存入缓存中失败】,the exception => {}", e);
//        }
        logger.debug("【保存数据到缓存中花费时间】：{}", System.currentTimeMillis() - currentTime);
        return checkUserLoginStatus(user);
    }

    /**
     * 检测用户登录状态
     * @param user 传入用户信息
     * @return 返回状态值
     */
    private int checkUserLoginStatus(User user) {
        try {
            User userInfo = userMapper.getUserByTel(user.getTelephone());
            if (userInfo == null) {
                logger.warn("【数据库中用户信息不存在】");
                logger.info("【用户注册开始】");
                userMapper.addUser(user);
                logger.info("【用户注册结束】");

                return UserCommonStatus.SUCCESS.getCode();
            } else {
                logger.error("【用户手机号码已被注册】");
                return UserCommonStatus.ERROR.getCode();
            }
        } catch (Exception e) {
            logger.error("【用户注册失败】=> {}", e.getMessage());
            throw new ServiceException(ErrorCode.DATABASE_ERROR);
        }
    }

    /**
     * 用户登录+短信验证码（手机号码+短信验证码）
     *
     * @param mobilePhoneCodeDto 传入储存参数集合
     * @return 返回登录结果
     * @throws Exception 抛出异常
     */
    @Override
    public User loginWithCode(MobilePhoneCodeDto mobilePhoneCodeDto) throws Exception {
        String telephoneNumber = mobilePhoneCodeDto.getMobilePhoneNumber();
        User isLoginUser = userMapper.getUserByTel(telephoneNumber);
        if (isLoginUser != null) {
            String code = mobilePhoneCodeDto.getIdentityCode();//判断验证码是否正确以及验证码是否有效，是否超时
            if (code.equals("123456")) {
                return isLoginUser;
            }
        }
        return null;
    }

    /**
     * 用户注销方法实现
     *
     * @param userInfoDto 传入存储参数集合
     * @return 放回注销状态
     * @throws Exception 抛出异常
     */
    @Override
    public int deleteUser(UserInfoDto userInfoDto) throws Exception {
        userMapper.deleteUser(userInfoDto.getId());
//        return UserCommonStatus.getCodeByName("SUCCESS");
        return Result.SUCCESS_CODE;
    }

    /**
     * 获取用户基本信息方法实现
     *
     * @param userInfoDto 传入存储参数集合
     * @return 放回注销状态
     * @throws Exception 抛出异常
     */
    @Override
    public UserInfoDto getBasicUser(UserInfoDto userInfoDto) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = userMapper.getUserByUserID(userInfoDto.getId());
        UserInfoDto userInfo = new UserInfoDto();
        userInfo.setNickname(user.getNickname());
        userInfo.setTelephone(user.getTelephone());
        userInfo.setSex(user.getSex());
        userInfo.setTotalOrderCount(user.getTotalOrderCount());
        userInfo.setSuccessOrderCount(user.getSuccessOrderCount());
        userInfo.setFailOrderCount(user.getFailOrderCount());
        return userInfo;
    }

    /**
     * 用户实名制方法实现
     *
     * @param userInfoDto 传入存储结果参数
     * @return 返回User对象
     * @throws Exception 抛出异常
     */
    @Override
    public void realUser(UserInfoDto userInfoDto) throws Exception {
        User user = userMapper.getUserByUserID(userInfoDto.getId());
        /*user.setNickname(String.valueOf(paramMap.get("nickname")));
        user.setTelephone(String.valueOf(paramMap.get("telephone")));
        user.setPassword(String.valueOf(paramMap.get("password")));
        user.setSex(Boolean.parseBoolean(String.valueOf(paramMap.get("sex"))));
        user.setAddress(String.valueOf(paramMap.get("address")));
        user.setName(String.valueOf(paramMap.get("name")));*/
        //用户默认收货地址
        user.setAddress(userInfoDto.getAddress());
        user.setId_card(userInfoDto.getId_card());
        user.setIdCardImage(ImageUtil.changeToStream(userInfoDto.getIdCardImage()));
        user.setStuCardImage(ImageUtil.changeToStream(userInfoDto.getStuCardImage()));
        userMapper.identityUserInfo(user);
    }

    /**
     * 手机号码获取验证码
     *
     * @param mobilePhoneCodeDto 传入存储结果参数
     * @return 返回验证码
     * @throws Exception 抛出异常
     */
    @Override
    public String getCode(MobilePhoneCodeDto mobilePhoneCodeDto) throws Exception {
        String telephone = mobilePhoneCodeDto.getMobilePhoneNumber();
        boolean getCode = Boolean.parseBoolean(mobilePhoneCodeDto.getIdentityCode());//获取短信验证码
        if (telephone != null && getCode) {
            return "123456";
        } else {
            return null;
        }

    }


    /**
     * 查看用户信息是否完整
     *
     * @param receiveOrderDto
     * @return
     * @throws Exception
     */
    @Override
    public User viewIfFullUserInformation(ReceiveOrderDto receiveOrderDto) throws Exception {
        User user = userMapper.getUserByUserID(Long.valueOf(receiveOrderDto.getReceiverID()));
        if (user == null) {
            logger.error("【用户信息不完善】");
            return null;
        } else {
            return user;
        }
    }

    /**
     * 用户重置密码
     *
     * @param userInfoDto 传入存储结果参数
     * @return 返回更改状态
     * @throws Exception 抛出异常
     */
    @Override
    public int resetPwd(UserInfoDto userInfoDto) throws Exception {
        User user = userMapper.getUserByTel(userInfoDto.getTelephone());
        user.setPassword(user.getPassword());
        userMapper.changeUserInfo(user);
        return Result.SUCCESS_CODE;
    }

    /**
     * 获取用户全部信息方法实现
     *
     * @param userInfoDto 传入存储结果参数
     * @return 返回用户User信息
     * @throws Exception 抛出异常
     */
    @Override
    public User getAllInfo(UserInfoDto userInfoDto) throws Exception {
        User user = userMapper.getUserByTel(userInfoDto.getTelephone());
        if (user != null) {
            user.setStuCardImage(null);
            user.setIdCardImage(null);
            user.setPassword("");
        }
        return user;
    }

    /**
     * 获取用户取货地址方法实现
     *
     * @param userInfoDto 传入储存参数集合
     * @return 返回具体地址信息
     * @throws Exception 抛出异常
     */
    @Override
    public String getAddress(UserInfoDto userInfoDto) throws Exception {
        User user = userMapper.getUserByUserID(userInfoDto.getId());
        return user.getAddress();
    }

    /**
     * 用户登录业务方法实现（手机号码+密码）
     *
     * @param userInfoDto 传入存储参数集合
     * @return 返回int类型
     */
    @Override
    public User loginUserWithPwd(UserInfoDto userInfoDto) throws Exception {
        User user = new User();
        user.setTelephone(userInfoDto.getTelephone());
        user.setPassword(userInfoDto.getPassword());
        User isLoginUser = userMapper.getUserByTel(userInfoDto.getTelephone());
        if (isLoginUser == null) {
            logger.error("【用户信息不存在】");
            return null;
        } else {
            if (isLoginUser.getPassword().equals(user.getPassword())) {
                return isLoginUser;
            }
        }
        return null;
    }

    public User getUserBasicInfo(User user) throws Exception {
        logger.info("【获取用户基本信息】 => {}.", user.getId());
        user.setPassword("");
        user.setIdCardImage(null);
        user.setStuCardImage(null);
        return user;
    }
}

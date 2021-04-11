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
import spingboot.express.utils.PhoneNumberCheckUtils;
import spingboot.express.utils.RandomUtil;

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
//        logger.debug("【保存数据到缓存中花费时间】：{}", System.currentTimeMillis() - currentTime);
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
            if (userInfo != null) {
                logger.error("【用户手机号码已被注册】");
                return UserCommonStatus.ERROR.getCode();
            } else {
                logger.warn("【数据库中用户信息不存在】");
                logger.info("【用户注册开始】");
                userMapper.addUser(user);
                logger.info("【用户注册结束】");
                return UserCommonStatus.SUCCESS.getCode();
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
    public UserInfoDto loginWithCode(MobilePhoneCodeDto mobilePhoneCodeDto) throws Exception {
        String telephoneNumber = mobilePhoneCodeDto.getMobilePhoneNumber();
        boolean isLegal = PhoneNumberCheckUtils.isLegal(telephoneNumber);
        if (isLegal) {
            User isLoginUser = userMapper.getUserByTel(telephoneNumber);
            if (!isLoginUser.getTelephone().isEmpty()) {
                String code = mobilePhoneCodeDto.getIdentityCode();
                // 判断验证码是否正确以及验证码是否有效，是否超时
                // 这里我们将redis缓存中的数据取出来，之后，判断是否过期，来更新登录状态

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
        int deleteID = userMapper.deleteUser(userInfoDto.getId());
        if (deleteID == 1) {
            logger.info("delete user success!!");
            return UserCommonStatus.SUCCESS.getCode();
        } else {
            logger.error("delete user failed!!");
            return UserCommonStatus.FAIL.getCode();
        }
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
        logger.info("获取用户信息开始，入库查询");
        long currentTime = System.currentTimeMillis();
        User user = userMapper.getUserByUserID(userInfoDto.getId());
        logger.info("time spend -> {}", System.currentTimeMillis() - currentTime);
        UserInfoDto userInfo = new UserInfoDto();
        if (user != null) {
            userInfo.setNickname(user.getNickname());
            userInfo.setTelephone(user.getTelephone());
            userInfo.setSex(user.getSex());
            userInfo.setTotalOrderCount(user.getTotalOrderCount());
            userInfo.setSuccessOrderCount(user.getSuccessOrderCount());
            userInfo.setFailOrderCount(user.getFailOrderCount());
            return userInfo;
        }
        return null;
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
    public int getCode(MobilePhoneCodeDto mobilePhoneCodeDto) throws Exception {
        String telephone = mobilePhoneCodeDto.getMobilePhoneNumber();
        boolean isLegal = PhoneNumberCheckUtils.isLegal(telephone);
        boolean getCode = mobilePhoneCodeDto.isGetCodeButton();//获取短信验证码
        int code;
        if (isLegal && getCode) {
            code = RandomUtil.getSixRandomNum();
        } else {
            code = 0;
        }
        return code;
    }


    /**
     * 查看用户信息是否完整
     *
     * @param receiveOrderDto
     * @return
     * @throws Exception
     */
    @Override
    public UserInfoDto viewIfFullUserInformation(ReceiveOrderDto receiveOrderDto) throws Exception {
        User user = userMapper.getUserByUserID((long) receiveOrderDto.getReceiverID());
        String idCardNumber = user.getId_card();
        if (idCardNumber.isEmpty()) {
            logger.warn("The user is not identify, please identify!!!!!!!");
            // 可以提醒用户进行实名制认证
            return null;
        } else {
            UserInfoDto userInfoDto = new UserInfoDto();
            userInfoDto.setId_card(user.getId_card());
            return userInfoDto;
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
    public UserInfoDto loginUserWithPwd(UserInfoDto userInfoDto) throws Exception {
        UserInfoDto userInfo = new UserInfoDto();
        User isLoginUser = userMapper.getUserByTel(userInfoDto.getTelephone());
        logger.info("get userInfo => {}", isLoginUser.toString());
        String telephone = isLoginUser.getTelephone();
        if (telephone.isEmpty()) {
            logger.error("【用户信息不存在】");
            userInfo.setTelephone(null);
            return userInfo;
        } else {
            if (isLoginUser.getPassword().equals(userInfoDto.getPassword())) {
                userInfo.setTelephone(isLoginUser.getTelephone());
                userInfo.setNickname(isLoginUser.getNickname());
                userInfo.setId(isLoginUser.getId());
                return userInfo;
            } else {
                logger.warn("user password is wrong!!!!!");
                return null;
            }
        }
    }

    @Override
    public User getUserBasicInfo(User user) throws Exception {
        logger.info("【获取用户基本信息】 => {}.", user.getId());
        user.setPassword("");
        user.setIdCardImage(null);
        user.setStuCardImage(null);
        return user;
    }
}

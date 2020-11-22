package spingboot.express.commons;

/**
 * 用户模块通用状态枚举
 */
public enum UserCommonStatus {

    /**
     *（登录成功，注册成功，用户完善信息成功，用户注销成功）
     */
    SUCCESS(222, "成功"),

    /**
     * 后端服务器出错
     */
    FAIL(600, "内部错误"),

    /**
     *（验证码不匹配、验证码要设置填写时间限制，超时会不匹配）
     */
    NOT_MATCHING(500, "不匹配"),

    /**
     *（验证码失效）
     */
    USELESS(400, "已失效"),

    /**
     *（用户手机号码错误，用户登录密码错误，用户传入的基本信息有错误）
     */
    ERROR(300, "有错误"),

    /**
     *（用户已经注册、登录，再次进行注册登录）
     */
    REPEAT(200, "重复"),

    /**
     *用户信息不存在（用户未注册、登录之前进行其他操作）
     */
    NOT_EXIST(100, "不存在");

    private int code;

    private String name;

    private UserCommonStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }


    public static String getNameByCode(int code) {
        String name = "";
        for (UserCommonStatus userCommonStatus : UserCommonStatus.values()){
            name = String.valueOf(code);
        }
        return name;
    }

    public static int getCodeByName(String name){
        return UserCommonStatus.valueOf(name).code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package spingboot.express.enums;


/**
 * 请求错误对应code类
 */
public enum ErrorCode {

    MOBILE_PHONE_INVALID(1000, "用户手机号无效"),
    PASSWORD_INVALID(1001, "密码无效"),
    TOKEN_INVALID(1002, "token值失效"),

    USER_NAME_PWD_ERROR(2000, "用户名/密码错误"),
    DATABASE_ERROR(2001, "数据库层错误"),



    MOBILE_PHONE_USED_BY_OTHERS(3000, "手机号已被其它用户注册"),
    USER_INFO_NOTFOUND(3001, "用户信息查找不到"),
    USER_DISABLED(3002, "用户已注销"),

    USER_INACTIVE(4000, "用户未注册"),


    SERVICE_CALL_FAILED(5000, "服务器调用失败"),
    USER_SIGN_UP_FAILED(5001, "用户注册失败"),
    USER_DISABLED_FAILED(5002, "用户注销失败"),
    GET_VALID_CODE_FAILED(5003, "用户获取验证码失败"),
    PASSWORD_RESET_FAILED(5004, "用户密码重置失败"),
    USER_IDENTITY_FAILED(5005,"用户实名制失败"),
    USER_SIGN_IN_FAILED(5006, "用户登录失败");


    private int errorCode;
    private String errorMessage;


    ErrorCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "{" +
                "errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

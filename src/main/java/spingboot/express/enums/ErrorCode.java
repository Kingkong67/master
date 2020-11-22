package spingboot.express.enums;


/**
 * 请求错误对应code类
 */
public enum ErrorCode {

    MOBILE_PHONE_INVALID(1000, "用户手机号无效"),
    PASSWORD_INVALID(1001, "密码无效"),

    USER_NAME_PWD_ERROR(3000, "用户名/密码错误"),
    USER_INFO_NOTFIND(3001, "用户信息查找不到"),
    TOKEN_INVALID(3001, "token值失效"),

    MOBILE_PHONE_USED_BY_OTHERS(5000, "手机号已被其它用户注册"),
    USER_DISABLED(5001, "用户已注销"),
    USER_INACTIVE(5002, "用户未注册"),

    DATABASE_ERROR(8888, "数据库层错误"),
    SERVICE_CALL_FAILED(9999, "服务器调用失败");


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

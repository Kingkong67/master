package spingboot.express.enums;

/**
 * @Description:
 * @Author: wanghu
 * @Date: Create in 2020 11 2020/11/25
 * @return:
 * @throws:
 */
public enum OrderCommonStatus {

    SUCCESS(200,"请求操作成功"),

    ERROR(500,"请求失败，服务器错误"),

    FAIL(300,"请求过时，请重新刷新页面"),

    WARN(400,"用户未实名，请求失败");

    int code;

    String message;

    private OrderCommonStatus(int code,String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String getMessageByCode(int code) {
        String name = "";
        for (OrderCommonStatus orderCommonStatus : OrderCommonStatus.values()){
            name = String.valueOf(code);
        }
        return name;
    }

    public static int getCodeByName(String message){
        return OrderCommonStatus.valueOf(message).code;
    }
}

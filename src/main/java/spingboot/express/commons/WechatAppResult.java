package spingboot.express.commons;


import lombok.Data;

@Data
public class WechatAppResult extends Result {

    /**
     * 用户唯一标识
     */

    private String openId;

    /**
     * 会话密钥
     */
    private String session_key;

    /**
     * 用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。
     */
    private String unionId;

    /**
     * 错误码
     */
    private String errCode;

    /**
     * 错误信息
     */
    private String errMsg;

    public WechatAppResult(){
        super();
    }

    @Override
    public String toString() {
        return "WechatAppResult{" +
                "openId='" + openId + '\'' +
                ", session_key='" + session_key + '\'' +
                ", unionId='" + unionId + '\'' +
                ", errCode='" + errCode + '\'' +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}

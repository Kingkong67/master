package spingboot.express.dto;


import lombok.Data;

@Data
public class WechatAuthResult {

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
     * 返回用户需要登录使用的token值
     */
    private String tokenId;

    /**
     * 错误码
     */
    private int errCode;

    /**
     * 错误信息
     */
    private String errMsg;

    public WechatAuthResult() {
    }

    @Override
    public String toString() {
        return "WechatAuthResult{" +
                "openId='" + openId + '\'' +
                ", session_key='" + session_key + '\'' +
                ", tokenId='" + tokenId + '\'' +
                ", errCode=" + errCode +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}

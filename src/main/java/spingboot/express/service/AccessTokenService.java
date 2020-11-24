package spingboot.express.service;


/**
 * 生成token服务service层
 */
public interface AccessTokenService {

    /**
     * 生成token值
     *
     * @param platformCode platformCode
     * @param userCode     userCode
     * @return
     */
    String createAccessTokenByUserCode(String platformCode, String userCode);

    /**
     * 创建refreshToken
     *
     * @param userCode     userCode
     * @param platformCode platformCode
     * @return
     */
    String createRefreshToken(String userCode, String platformCode);


    /**
     * 检查refreshToken是否有效
     *
     * @param platformCode platformCode
     * @param userCode     userCode
     * @param refreshToken refreshToken
     * @return
     */
    boolean isRefreshTokenValid(String platformCode, String userCode, String refreshToken);

    /**
     * 检查token是否有效
     *
     * @param token     token
     * @param secret    secret
     * @param timeStamp timeStamp
     * @param userCode  userCode
     * @return
     */
    @Deprecated
    boolean isTokenValid(String token, String secret, long timeStamp, String userCode);

    /**
     * 检查token是否有效
     *
     * @param userCode     userCode
     * @param platformCode platformCode
     * @param token        token
     * @return userCode
     */
    String isAccessTokenValid(String userCode, String platformCode, String token);
}

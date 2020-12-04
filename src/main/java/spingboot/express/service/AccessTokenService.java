package spingboot.express.service;


import spingboot.express.constant.PlatformKey;

/**
 * 生成token服务service层
 */
public interface AccessTokenService {

    /**
     *
     * @param platformKey
     * @param userCode
     * @return
     */
    String createAccessTokenByUserCode(PlatformKey platformKey, String userCode);

    /**
     * 创建refreshToken
     *
     * @param userCode     userCode
     * @param platformKey  platformKey
     * @return
     */
    String createRefreshToken(String userCode, PlatformKey platformKey);


    /**
     * 检查refreshToken是否有效
     *
     * @param platformKey platformKey
     * @param userCode     userCode
     * @param refreshToken refreshToken
     * @return
     */
    boolean isRefreshTokenValid(PlatformKey platformKey, String userCode, String refreshToken);

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
     * @param platformKey platformKey
     * @param token        token
     * @return userCode
     */
    String isAccessTokenValid(String userCode, PlatformKey platformKey, String token);
}

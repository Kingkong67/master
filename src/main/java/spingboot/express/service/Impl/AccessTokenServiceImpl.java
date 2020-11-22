package spingboot.express.service.Impl;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spingboot.express.constant.TimeRange;
import spingboot.express.service.AccessTokenService;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;


/**
 * 生成token服务service-impl层
 */
@Service
@Slf4j
public class AccessTokenServiceImpl implements AccessTokenService {

    private byte[] jwtRefreshTokenSecretByte;

    @Value("${jwt.id}")
    private String jwtId;

    @Value("${jwt.access.token.secret}")
    private String jwtAccessTokenSecret;

    @Value("${jwt.refresh.secret}")
    public void setJwtRefreshTokenSecret(String jwtRefreshTokenSecret) {
        this.jwtRefreshTokenSecretByte = Base64.decode(jwtRefreshTokenSecret);
    }




    /**
     * 生成token值
     *
     * @param platformCode platformCode
     * @param userCode     userCode
     * @return
     */
    @Override
    public String createAccessTokenByUserCode(String platformCode, String userCode) {
        return createAccessToken(userCode, platformCode);
    }

    /**
     * 创建refreshToken
     *
     * @param userCode     userCode
     * @param platformCode platformCode
     * @return
     */
    @Override
    public String createRefreshToken(String userCode, String platformCode) {
        return null;
    }


    /**
     * 检查refreshToken是否有效
     *
     * @param platformCode platformCode
     * @param userCode     userCode
     * @param refreshToken refreshToken
     * @return
     */
    @Override
    public boolean isRefreshTokenValid(String platformCode, String userCode, String refreshToken) {
        return false;
    }


    /**
     * 检查token是否有效
     *
     * @param token     token
     * @param secret    secret
     * @param timeStamp timeStamp
     * @param userCode  userCode
     * @return
     */
    @Override
    public boolean isTokenValid(String token, String secret, long timeStamp, String userCode) {
        return false;
    }


    /**
     * 检查token是否有效
     *
     * @param userCode     userCode
     * @param platformCode platformCode
     * @param token        token
     * @return userCode
     */
    @Override
    public String isAccessTokenValid(String userCode, String platformCode, String token) {
        return null;
    }

    /**
     * 生成token方法
     *
     * @param userCode     userCode
     * @param platformCode platformCode
     * @return 返回token
     */
    private String createAccessToken(String userCode, String platformCode) {
        byte[] accessSecretByte = Base64.decode(jwtAccessTokenSecret);
        SecretKey key = new SecretKeySpec(accessSecretByte, 0, accessSecretByte.length, "AES");
        DefaultClaims defaultClaims = createDefaultClaims(userCode, platformCode);
        defaultClaims.put("userCode", userCode);
        defaultClaims.put("platformCode", platformCode);
        JwtBuilder jwtBuilder = Jwts.builder().setClaims(defaultClaims).signWith(SignatureAlgorithm.HS256, key);
        return jwtBuilder.compact();
    }

    /**
     * 获取到注册中的必要声明
     *
     * @param userCode     userCode
     * @param platformCode platformCode
     * @return
     */
    private DefaultClaims createDefaultClaims(String userCode, String platformCode) {
        DefaultClaims defaultClaims = new DefaultClaims();
        long current = System.currentTimeMillis();
        long expirationMills = current + TimeRange.TEN_MINUTES_MILLS;
        //关键代码
        defaultClaims.setId(this.jwtId).setSubject(userCode).setIssuedAt(new Date(current)).setAudience(platformCode)
                .setExpiration(new Date(expirationMills));
        return defaultClaims;
    }
}

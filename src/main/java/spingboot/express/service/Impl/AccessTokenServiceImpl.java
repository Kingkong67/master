package spingboot.express.service.Impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spingboot.express.constant.PlatformKey;
import spingboot.express.constant.TimeRange;
import spingboot.express.enums.ErrorCode;
import spingboot.express.exception.ServiceException;
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
    public String createAccessTokenByUserCode(PlatformKey platformCode, String userCode) {
        return createAccessToken(userCode, platformCode);
    }

    /**
     * 创建refreshToken
     *
     * @param userCode    userCode
     * @param platformKey platformKey
     * @return
     */
    @Override
    public String createRefreshToken(String userCode, PlatformKey platformKey) {
        long currentTime = System.currentTimeMillis();
        SecretKey key = new SecretKeySpec(jwtRefreshTokenSecretByte, 0, jwtRefreshTokenSecretByte.length, "AES");
        DefaultClaims defaultClaims = new DefaultClaims();
        defaultClaims.setId(this.jwtId).setSubject(userCode).setAudience(platformKey.name()).setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + TimeRange.ONE_DAY_MILLS));
        JwtBuilder jwtBuilder = Jwts.builder().setClaims(defaultClaims).signWith(SignatureAlgorithm.HS256, key);
        return jwtBuilder.compact();
    }


    /**
     * 检查refreshToken是否有效
     *
     * @param platformKey  platformKey
     * @param userCode     userCode
     * @param refreshToken refreshToken
     * @return
     */
    @Override
    public boolean isRefreshTokenValid(PlatformKey platformKey, String userCode, String refreshToken) {
        try {
            log.info("get check refreshToken.");
            Jws<Claims> claimsJws = Jwts.parser().requireSubject(userCode).requireAudience(platformKey.name())
                    .setSigningKey(this.jwtRefreshTokenSecretByte).parseClaimsJws(refreshToken);
            log.info("check refreshToken success, the refreshToken is {}.", refreshToken);
            return claimsJws.getBody().getExpiration().after(new Date());
        } catch (Exception e) {
            log.error("check the refreshToken failed, the exception is {}.", e.getMessage());
            return false;
        }
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
        String encrypted = DigestUtils.sha256Hex(secret + ":" + token + ":" + timeStamp);
        if (!encrypted.equals(token)) {
            log.error("user {} tries to login in with a invalid token {}.", userCode, token);
            return false;
        } else {
            return true;
        }
    }


    /**
     * 检查token是否有效
     *
     * @param userCode    userCode
     * @param platformKey platformKey
     * @param token       token
     * @return userCode
     */
    @Override
    public String isAccessTokenValid(String userCode, PlatformKey platformKey, String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            if (StringUtils.isNoneEmpty(userCode)) {
                jwtParser = jwtParser.requireSubject(userCode);
            }
            if (StringUtils.isNotEmpty(platformKey.name())) {
                jwtParser = jwtParser.requireAudience(platformKey.name());
            }
            Jws<Claims> claimsJwt = jwtParser.setSigningKey(Base64.decode(this.jwtAccessTokenSecret)).parseClaimsJws(token);
            if (claimsJwt.getBody().getExpiration().after(new Date())) {
                return claimsJwt.getBody().getSubject();
            }
            return null;
        } catch (Exception e) {
            log.error("The token is invalid, the exception is {} caused by {}.", e.getMessage(), e.getCause());
            throw new ServiceException(ErrorCode.TOKEN_INVALID, ErrorCode.TOKEN_INVALID.getErrorMessage());
        }
    }

    /**
     * 生成token方法
     *
     * @param userCode    userCode
     * @param platformKey platformKey
     * @return 返回token
     */
    private String createAccessToken(String userCode, PlatformKey platformKey) {
        byte[] accessSecretByte = Base64.decode(jwtAccessTokenSecret);
        SecretKey key = new SecretKeySpec(accessSecretByte, 0, accessSecretByte.length, "AES");
        DefaultClaims defaultClaims = createDefaultClaims(userCode, platformKey);
        defaultClaims.put("userCode", userCode);
        defaultClaims.put("platformKey", platformKey.name());
        JwtBuilder jwtBuilder = Jwts.builder().setClaims(defaultClaims).signWith(SignatureAlgorithm.HS256, key);
        return jwtBuilder.compact();
    }

    /**
     * 获取到注册中的必要声明
     *
     * @param userCode    userCode
     * @param platformKey platformKey
     * @return
     */
    private DefaultClaims createDefaultClaims(String userCode, PlatformKey platformKey) {
        DefaultClaims defaultClaims = new DefaultClaims();
        long current = System.currentTimeMillis();
        long expirationMills = determineExpirationMills(current, platformKey);
        //关键代码
        defaultClaims.setId(this.jwtId).setSubject(userCode).setIssuedAt(new Date(current)).setAudience(platformKey.name())
                .setExpiration(new Date(expirationMills));
        return defaultClaims;
    }

    /**
     * 根据platformCode判断过期时间
     *
     * @param current      current
     * @param platformKey platformCode
     * @return
     */
    private long determineExpirationMills(long current, PlatformKey platformKey) {
        long expirationMills = 0;
        switch (platformKey) {
            case WEBSITE:
                expirationMills = current + TimeRange.ONE_DAY_MILLS;
                break;
            case WECHAT:
                expirationMills = current + TimeRange.ONE_HOUR_MILLS;
                break;
            case MOBILE:
                expirationMills = current + TimeRange.TEN_MINUTES_MILLS;
                break;
            default:
                log.error("Unknown appKey: {}.", platformKey);
        }
        return expirationMills;
    }
}

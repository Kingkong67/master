package spingboot.express.facade;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spingboot.express.constant.Constants;
import spingboot.express.dto.RefreshTokenDto;
import spingboot.express.dto.auth.AuthRequest;
import spingboot.express.dto.auth.AuthResult;
import spingboot.express.enums.ErrorCode;
import spingboot.express.enums.UserCommonStatus;
import spingboot.express.exception.ServiceException;
import spingboot.express.service.AccessTokenService;
import spingboot.express.service.RedisCacheService;
import spingboot.express.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class LoginFacade {


    @Autowired
    private RedisCacheService redisCacheService;

    @Autowired
    private AccessTokenService accessTokenService;

    /**
     * 处理用户登录授权问题
     *
     * @param request     request
     * @param authRequest authRequest 该对象中包含的是创建出来的accessToken和refreshToken
     */
    public AuthResult signIn(HttpServletRequest request, AuthRequest authRequest) {
        String tokenId = request.getHeader("tokenId");
        log.info("Get tokenId {} from header.", tokenId);
        AuthResult authResult = new AuthResult();
        if (StringUtils.isEmpty(tokenId)) {
            String token = accessTokenService.createAccessTokenByUserCode(authRequest.getPlatformKey(), authRequest.getUserCode());
            log.info("The token is {} created right now.", token);
            redisCacheService.saveValue(Constants.AUTH_TOKEN_CODE + authRequest.getUserCode(), token);
            redisCacheService.saveObject(Constants.AUTH_USER_CODE + authRequest.getUserCode(), authRequest);
            authResult.setAccessToken(token);
        } else {
            String userCode = accessTokenService.isAccessTokenValid(authRequest.getUserCode(), authRequest.getPlatformKey(), tokenId);
            if (userCode == null) {
                log.warn("The token {} is invalid, please get accessToken again!.", tokenId);
                authResult.setCode(ErrorCode.TOKEN_INVALID.getErrorCode());
                authResult.setMessage(ErrorCode.TOKEN_INVALID.getErrorMessage());
                authResult.setIsSuccess(false);
            } else {
                AuthRequest authRequestInfo = redisCacheService.getObject(Constants.AUTH_USER_CODE + authRequest.getUserCode(), AuthRequest.class);
                log.info("The userCode in request header and in the cache is {} / {}.", userCode, authRequestInfo.getUserCode());
                if (userCode.equals(authRequestInfo.getUserCode())) {
                    //此时说明用户在 token 中携带的 userCode 和在缓存中的是一致的，说明，用户的token没有过期，可以进行后续操作
                    //将用户的userCode和accessToken返回
                    String token = redisCacheService.getValue(Constants.AUTH_TOKEN_CODE + authRequest.getUserCode());
                    authResult.setUserCode(userCode);
                    authResult.setAccessToken(token);
                    authResult.setCode(UserCommonStatus.SUCCESS.getCode());
                    authResult.setIsSuccess(true);
                } else {
                    //说明用户没有tokenId，同时缓存中也没有查到该用户的信息，所以，可以进行判断，该用户没有进行登录注册
                    authResult.setCode(ErrorCode.RETRY_SIGN_IN.getErrorCode());
                    authResult.setMessage(ErrorCode.RETRY_SIGN_IN.getErrorMessage());
                    authResult.setIsSuccess(false);
                }
            }
        }
        return authResult;

    }

    /**
     * 创建refreshToken
     *
     * @param refreshTokenDto refreshTokenDto数据传递Dto
     * @return
     */
    public String refreshToken(RefreshTokenDto refreshTokenDto) {
        String userCode = accessTokenService.isAccessTokenValid(refreshTokenDto.getUserCode(), refreshTokenDto.getPlatformKey(), refreshTokenDto.getAccessToken());
        if (StringUtils.isEmpty(userCode)) {
            log.error("check User {} failed.", userCode);
        }
        if (userCode.equals(refreshTokenDto.getUserCode())) {
            log.info("User {} renew refresh token for platform {}", refreshTokenDto.getUserCode(), refreshTokenDto.getPlatformKey());
            return accessTokenService.createRefreshToken(refreshTokenDto.getUserCode(), refreshTokenDto.getPlatformKey());
        } else {
            throw new ServiceException(ErrorCode.TOKEN_INVALID, ErrorCode.TOKEN_INVALID.getErrorMessage());
        }
    }
}

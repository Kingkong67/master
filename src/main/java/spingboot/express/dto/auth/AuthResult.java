package spingboot.express.dto.auth;

import lombok.Data;
import spingboot.express.commons.Result;
import spingboot.express.constant.PlatformKey;

@Data
public class AuthResult extends Result {
    private String userCode;
    private PlatformKey platformKey;
    private String accessToken;
    private String refreshToken;

    public AuthResult() {}
}

package spingboot.express.dto.auth;

import lombok.Data;
import spingboot.express.constant.PlatformKey;

@Data
public class AuthResult {
    private String userCode;
    private PlatformKey platformKey;
    private String accessToken;
    private String refreshToken;

    public AuthResult() {}
}

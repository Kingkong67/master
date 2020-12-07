package spingboot.express.dto;

import lombok.Data;
import spingboot.express.constant.PlatformKey;

@Data
public class RefreshTokenDto {

    private PlatformKey platformKey;
    private String userCode;
    private String accessToken;
}

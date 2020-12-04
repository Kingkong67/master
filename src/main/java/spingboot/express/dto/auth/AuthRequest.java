package spingboot.express.dto.auth;

import lombok.Data;
import org.springframework.context.annotation.PropertySource;
import spingboot.express.constant.PlatformKey;

@Data
public class AuthRequest {
    private PlatformKey platformKey;
    private String userCode;
    private String password;
}

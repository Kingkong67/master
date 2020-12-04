package spingboot.express.dto.auth;

import lombok.Data;

@Data
public class LoginResult {

    private boolean isSuccess;
    private String loginCode;
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

}

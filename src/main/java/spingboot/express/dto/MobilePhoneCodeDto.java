package spingboot.express.dto;

import lombok.Data;

@Data
public class MobilePhoneCodeDto {

    private String mobilePhoneNumber;

    private boolean isCode;

    private String identityCode;
}

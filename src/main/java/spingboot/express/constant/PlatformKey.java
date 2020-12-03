package spingboot.express.constant;

import org.apache.commons.lang3.StringUtils;

public enum PlatformKey {
    WEBSITE,
    MOBILE,
    WECHAT;

    public static PlatformKey getCode(String platformCode) {
        if (StringUtils.isEmpty(platformCode)) {
            return null;
        }
        return PlatformKey.valueOf(platformCode.toUpperCase());
    }
}

package spingboot.express.utils;

public class CodeMakerUtil {

    private static final String PLATFORM_CODE_PREFIX = "PLAT";

    private static final String USER_CODE_PREFIX = "USER";


    public static String createUserCode() {
        return USER_CODE_PREFIX + System.currentTimeMillis();
    }

    public static String createPlatformCode() {
        return PLATFORM_CODE_PREFIX + System.currentTimeMillis();
    }


}

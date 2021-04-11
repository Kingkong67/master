package spingboot.express.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证手机号码合法性的工具类
 */
public class PhoneNumberCheckUtils {

    public static boolean isLegal(String phoneNumber) {
        if (phoneNumber.length() != 11) {
            return false;
        }
        // 这里我们使用正则表达式来进行号码的正确性检验
        String regex = "0\\d{2,3}[-]?\\d{7,8}|0\\d{2,3}\\s?\\d{7,8}|1\\d{10}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}

package spingboot.express.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Description:
 * @Author: wanghu
 * @Date: Create in 2021 02 2021/2/16
 * @return:
 * @throws:
 */
public class Configuration {
    //常用来配置文件，key和value都是string类型的
    private static final Properties p = new Properties();

    private static final Map<Integer,String> cityMap = new HashMap<>();

    static{
        try {
            initFromFile();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        if (!p.containsKey(key)) {
            return "";
        }
        return p.getProperty(key);
    }

    public static String getCityName(int i) {
        if (!cityMap.containsKey(i)) {
            return String.valueOf(i);
        }else{
            return cityMap.get(i);
        }
    }

    public static String getString(String name) {
        if (p.containsKey(name)) {
            return p.getProperty(name);
        }
        throw new IllegalArgumentException("Missing required property '"+name+"'");
    }

    public static int getInt(String name) {
        if (p.containsKey(name)) {
            return Integer.valueOf(p.getProperty(name));
        }
        throw new IllegalArgumentException("Missing required property '"+name+"'");
    }

    public static List<String> getList(String name) {
        if (p.containsKey(name)) {
            List<String> keys = new ArrayList<>();
            String names = p.getProperty(name);
            for(String str : names.split(",")) {
                keys.add(str.trim());
            }
            if (keys.size() > 0) {
                return keys;
            }
        }
        throw new IllegalArgumentException("Missing required property '"+name+"'");
    }

    private static void initFromFile() throws IOException {

    }
}

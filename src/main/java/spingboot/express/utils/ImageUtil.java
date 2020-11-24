package spingboot.express.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 将上传的图片文件信息转化为二进制输入流工具类
 */
public class ImageUtil {
    /**
     * 该方法将图片以文件路径的形式更改为二进制流
     *
     * @param path 传入文件路径
     * @return 返回二进制流
     */
    public static byte[] changeToStream(String path) {
        byte[] imageBytes = null;
        try (FileInputStream fileInputStream = new FileInputStream(new File(path))) {
            imageBytes = new byte[fileInputStream.available()];
            fileInputStream.read(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageBytes;
    }
}

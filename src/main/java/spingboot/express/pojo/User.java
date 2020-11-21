package spingboot.express.pojo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * 用户模块pojo
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 4448099319617060855L;
    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户手机号码
     */
    private String telephone;
    /**
     * 用户性别
     * true表示：男
     * false表示：女
     */
    private Boolean sex;
    /**
     * 用户真实姓名
     */
    private String name;
    /**
     * 用户身份证号
     */
    private String id_card;
    /**
     * 用户身份证件照
     */
    private byte[] idCardImage;
    /**
     * 用户一卡通照片
     */
    private byte[] stuCardImage;
    /**
     * 用户默认地址
     */
    private String address;
    /**
     * 用户创建时间
     */
    private Timestamp create_time;
    /**
     * 用户订单总数
     */
    private int totalOrderCount;
    /**
     * 用户成功单数
     */
    private int successOrderCount;
    /**
     * 用户失败单数
     */
    private int failOrderCount;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                ", sex=" + sex +
                ", name='" + name + '\'' +
                ", id_card='" + id_card + '\'' +
                ", idCardImage=" + Arrays.toString(idCardImage) +
                ", stuCardImage=" + Arrays.toString(stuCardImage) +
                ", address='" + address + '\'' +
                ", create_time=" + create_time +
                ", totalOrderCount=" + totalOrderCount +
                ", successOrderCount=" + successOrderCount +
                ", failOrderCount=" + failOrderCount +
                '}';
    }
}

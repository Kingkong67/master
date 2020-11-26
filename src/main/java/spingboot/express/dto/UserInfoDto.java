package spingboot.express.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserInfoDto {

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
     * 用户身份证件照路径
     * 后期根据具体要求进行更改
     */
    private String idCardImage;
    /**
     * 用户一卡通照片路径
     * 后期根据具体要求进行更改
     */
    private String stuCardImage;
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

}

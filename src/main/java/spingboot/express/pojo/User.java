package spingboot.express.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * 用户模块pojo
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public byte[] getIdCardImage() {
        return idCardImage;
    }

    public void setIdCardImage(byte[] idCardImage) {
        this.idCardImage = idCardImage;
    }

    public byte[] getStuCardImage() {
        return stuCardImage;
    }

    public void setStuCardImage(byte[] stuCardImage) {
        this.stuCardImage = stuCardImage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public int getTotalOrderCount() {
        return totalOrderCount;
    }

    public void setTotalOrderCount(int totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
    }

    public int getSuccessOrderCount() {
        return successOrderCount;
    }

    public void setSuccessOrderCount(int successOrderCount) {
        this.successOrderCount = successOrderCount;
    }

    public int getFailOrderCount() {
        return failOrderCount;
    }

    public void setFailOrderCount(int failOrderCount) {
        this.failOrderCount = failOrderCount;
    }

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

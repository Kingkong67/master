package spingboot.express.pojo;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: wanghu
 * @Date: Create in 2020 11 2020/11/22
 * @return:
 * @throws:
 */
@Data
public class orderSnedInfo implements Serializable {


    private static final long serialVersionUID = -4491002682076826053L;

    /**
     * 订单ID
     */
    private Integer ID;

    /**
     * 发货人ID
     */
    private Integer senderID;

    /**
     * 发货人的电话
     */
    private String tel;

    /**
     * 收货人电话
     */
    private String receiveTel;

    /**
     * 货物取货地址
     */
    private String shipAddress;

    /**
     * 货物送货地址
     */
    private String sendAddress;

    /**
     * 额外补充
     */
    private String note;

    /**
     * 酬金
     */
    private String money;

    /**
     * 性别（0表示男，1表示女）
     */
    private String sex;

    /**
     * 收货截止日期
     */
    private Date deadLine;

    /**
     * 货物创建日期
     */
    private Date createTime;

    /**
     * 货物大小
     */
    private String size;

    /**
     * 是否有效（0表示无效，1表示有效）
     */
    private String isValid;

    /**
     * 订单状态
     */
    private Integer infoStatus;

    @Override
    public String toString() {
        return "orderSnedInfo{" +
                "ID=" + ID +
                ", senderID=" + senderID +
                ", tel='" + tel + '\'' +
                ", receiveTel='" + receiveTel + '\'' +
                ", shipAddress='" + shipAddress + '\'' +
                ", sendAddress='" + sendAddress + '\'' +
                ", note='" + note + '\'' +
                ", money='" + money + '\'' +
                ", 发单人性别='" + sex + '\'' +
                ", 订单截止时间=" + deadLine +
                ", 订单创建时间=" + createTime +
                ", 订单型号='" + size + '\'' +
                ", 是否有效='" + isValid + '\'' +
                ", 订单状态=" + infoStatus +
                '}';
    }
}

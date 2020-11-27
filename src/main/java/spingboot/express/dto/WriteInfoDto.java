package spingboot.express.dto;

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
public class WriteInfoDto implements Serializable {

    private static final long serialVersionUID = 993707896101209416L;

    /**
     * 订单ID
     */
    private Integer id;

    /**
     * 订单接收电话
     */
    private String tel;

    /**
     * 收货地址
     */
    private String shipAddress;

    /**
     * 发货地址
     */
    private String sendAddress;

    /**
     * 酬金
     */
    private String money;

    /**
     * 发单人性别（0表示男，1表示女）
     */
    private String sex;

    /**
     * 额外补充
     */
    private String note;

    /**
     * 截止日期
     */
    private Date deadLine;

    /**
     * 货物型号
     */
    private String size;

    /**
     * 接单人ID
     */
    private String receiverID;

    /**
     * 发货人ID
     */
    private String senderID;
    ;

    /**
     * 接单人电话
     */
    private String receiveTel;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 货物创建日期
     */
    private Date createTime;

    /**
     * 是否有效（0表示无效，1表示有效）
     */
    private String isValid;

    /**
     * 接单时间
     */
    private Date receiveTime;

    @Override
    public String toString() {
        return "电话：" + tel + "  取件地址：" + shipAddress + "  送货地址：" + sendAddress + "  工资：" + money + "  备注：" + note
                + "型号：" + size + "截止时间：" + deadLine + "接单人电话：" + receiveTel + "接单人ID:" + receiverID
                + "订单状态ID:" + orderStatus + "发单人ID:" + senderID + "创建时间：" + createTime + "是否有效：" + isValid;
    }
}

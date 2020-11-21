package spingboot.express.pojo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
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
public class orderReceiveInfo implements Serializable {

    private static final long serialVersionUID = 5560608114763878408L;

    /**
     * 订单ID
     */
    private Integer ID;

    /**
     * 收货人ID
     */
    private Integer ReceiverID;

    /**
     * 货物取货地址
     */
    private String shipAddress;

    /**
     * 货物送货地址
     */
    private String sendAddress;

    /**
     * 发货人的电话
     */
    private String tel;

    /**
     * 收货人电话
     */
    private String receiveTel;

    /**
     * 酬金
     */
    private String money;

    /**
     * 接单时间
     */
    private Date receiveTime;

    /**
     * 额外补充
     */
    private String note;

    /**
     * 截止时间
     */
    private Date deadTime;

    /**
     * 货物大小
     */
    private String size;
}

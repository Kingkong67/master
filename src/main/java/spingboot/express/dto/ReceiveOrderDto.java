package spingboot.express.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Author: wanghu
 * @Date: Create in 2020 11 2020/11/24
 * @return:
 * @throws:
 */

@Data
public class ReceiveOrderDto implements Serializable {
    private static final long serialVersionUID = 1709811267717114761L;

    /**
     * 订单ID
     */
    public Integer ID;

    /**
     * 接单人ID
     */
//    @NotEmpty(message = "接单人ID不为空")
    private int receiverID;

    /**
     * 接单人电话
     */
//    @NotEmpty(message = "接单人电话不为空")
    private String receiveTel;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 接单时间
     */
    private Date receiveTime;

}

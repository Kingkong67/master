package spingboot.express.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: wanghu
 * @Date: Create in 2020 11 2020/11/30
 * @return:
 * @throws:
 */
@Data
public class CancelOrderDto implements Serializable {

    private static final long serialVersionUID = -1155073564091992100L;

    /**
     * 订单ID
     */
    public Integer ID;

    /**
     * 发货人ID
     */
    private String senderID;
}

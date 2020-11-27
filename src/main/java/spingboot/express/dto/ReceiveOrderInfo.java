package spingboot.express.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Description:
 * @Author: wanghu
 * @Date: Create in 2020 11 2020/11/24
 * @return:
 * @throws:
 */

@Data
public class ReceiveOrderInfo implements Serializable {
    private static final long serialVersionUID = 1709811267717114761L;

//    @NotEmpty(message = "接单人ID不为空")
    private String receiverID;

//    @NotEmpty(message = "接单人电话不为空")
    private String receiveTel;
}

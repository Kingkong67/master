package spingboot.express.pojo;
/**
 * 信息模块pojo
 */

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 3263532385260724506L;

    /**
     * 订单接收电话
     */
    private String telephone;

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
     * 性别
     */
    private String sex;

    /**
     *
     */
    private String note;

    /**
     * 截止日期
     */
    private Date deadline;

    /**
     * 货物型号
     */
    private String size;

    /**
     *
     */
    private int receiverID;

    private int sendUserID;

    private String receiverTelephone;

    private int orderStatusID;

    private int id;

    private Date createTime;

    private int isValid;

    private Date receiveTime;

    @Override
    public String toString(){
        return "电话："+size+"  取件地址："+shipAddress+"  送货地址："+sendAddress+"  工资："+money+"  备注："+note+" 电话:"+telephone
                +"型号："+size+"截止时间："+deadline+"接单人电话："+receiverTelephone+"接单人ID:"+receiverID
                +"订单状态ID:"+orderStatusID+"发单人ID:"+sendUserID+"创建时间："+createTime;
    }
}

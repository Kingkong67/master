package spingboot.express.pojo;
/**
 * 信息模块pojo
 */

import java.io.Serializable;
import java.util.Date;

public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 3263532385260724506L;

    private String telephone;

    private String shipAddress;

    private String sendAddress;

    private String money;

    private String sex;

    private String note;

    private Date deadline;

    private String size;

    private int receiverID;

    private int sendUserID;

    private String receiver_telephone;

    private int orderStatusID;

    private int id;

    private Date createTime;

    private int isValid;

    private Date receiveTime;


    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public int getSendUserID() {
        return sendUserID;
    }

    public void setSendUserID(int sendUserID) {
        this.sendUserID = sendUserID;
    }

    public String getReceiver_telephone() {
        return receiver_telephone;
    }

    public void setReceiver_telephone(String receiver_telephone) {
        this.receiver_telephone = receiver_telephone;
    }

    public int getOrderStatusID() {
        return orderStatusID;
    }

    public void setOrderStatusID(int orderStatusID) {
        this.orderStatusID = orderStatusID;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDeadline() {return deadline; }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString(){
        return "电话："+size+"  取件地址："+shipAddress+"  送货地址："+sendAddress+"  工资："+money+"  备注："+note+" 电话:"+telephone
                +"型号："+size+"截止时间："+deadline+"接单人电话："+receiver_telephone+"接单人ID:"+receiverID
                +"订单状态ID:"+orderStatusID+"发单人ID:"+sendUserID+"创建时间："+createTime;
    }
}

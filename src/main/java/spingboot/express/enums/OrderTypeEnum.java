package spingboot.express.enums;

/**
 * 订单状态枚举
 */
public enum OrderTypeEnum {

    NOT_RECEIVED(1,"未领取","订单处于未被领取状态"),
    RECEIVED(2,"已领取","订单已被领取"),
//    SENDER_CANCELS_THE_ORDER(3,"取消发单","发单人取消发单"),
//    RECEIVER_CANCELS_THE_ORDER(4,"取消接单","接单人取消接单"),
    RECEIVER_RECEIVED_THE_ORDER(5,"拿到订单","接单人拿到订单"),
    RECEIVER_CONFIRM_DELIVERY(6,"已送达","接单人已送达订单"),
    NOT_CONFIRMED(7,"未收到","发单人点击未收到订单"),
    CONFIRMED(8,"已收到","发单人点击已收到订单");
//    COMPLETED(9,"已完成","订单已完成"),
//    NOT_COMPLETED(10,"未完成","订单未完成");

    private int code;
    private String name;
    private String desc;

    OrderTypeEnum(int code, String name, String desc){
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    /**
     * 根据code获取name
     * @param code
     * @return
     */
    public static String getNameByCode(int code) {
        String name = "";
        for (OrderTypeEnum orderTypeEnum : OrderTypeEnum.values()){
            name = String.valueOf(code);
        }
        return name;
    }

    /**
     * 根据name获取code
     * @param name
     * @return
     */
    public static int getCodeByName(String name){
        return OrderTypeEnum.valueOf(name).code;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}

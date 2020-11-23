package spingboot.express.mapper;
/**
 * 信息模块mybatis接口
 */


import org.springframework.stereotype.Repository;
import spingboot.express.pojo.OrderInfo;

import java.util.List;

@Repository
//@Mapper
public interface OrderMapper {
    /**
     * function:发单人填写发单信息的接口
     * @param orderInfo
     */
     int addInfo(OrderInfo orderInfo);

    /**
     * 查询所有订单接口
     * @return
     */
     List<OrderInfo> findAllInfo();

    /**
     * 接单用户接单pojo
     * @param orderInfo
     * @return
     */
     int order(OrderInfo orderInfo);

    /**
     * 查找用户的发单列表
     * @param orderInfo
     * @return
     */
    List<OrderInfo> findsendInfo(OrderInfo orderInfo);

    /**
     * 查找用户的接单列表
     * @param orderInfo
     * @return
     */
    List<OrderInfo> findgetInfo(OrderInfo orderInfo);

    /**
     * 检查订单状态
     * @param id
     * @return
     */
    OrderInfo checkInfo(int id);

    /**
     * 改变订单状态
     * @param orderInfo
     * @return
     */
    int changeInfostatus(OrderInfo orderInfo);

    /**
     * 查询某条订单
     * @param orderInfo
     * @return
     */
    List<OrderInfo> querySingleInfoOrder(OrderInfo orderInfo);

    /**
     * 发单用户取消发单，删除订单
     * @param orderInfo
     * @return
     */
     int deletesenderInfo(OrderInfo orderInfo);

    /**
     * 更新操作，在删除主键后使主键连续
     * @param id
     * @return
     */
     int update(int id);

    /**
     * 在添加主键时使主键连续
     * @param i
     * @return
     */
     int alterinfo(int i);

    /**
     * 接单用户取消接单,删除接单记录
     * @param orderInfo
     */
     void deletereceiverInfo(OrderInfo orderInfo);

    /**
     *发单人编辑订单
     * @param orderInfo
     * @return
     */
     int senderEditInfo(OrderInfo orderInfo);

    /**
     * 接单人编辑订单
     * @param orderInfo
     * @return
     */
     int geterEditInfo(OrderInfo orderInfo);

    /**
     * 改变订单有效性
     * @param id
     * @return
     */
     int changeisValidInfo(int id);

    /**
     * 用户长时间未点击确认，自动确认
     * @param id
     * @return
     */
     int updateOrderStatusById(int id);

}

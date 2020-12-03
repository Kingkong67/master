package spingboot.express.mapper;
/**
 * 信息模块mybatis接口
 */


import org.springframework.stereotype.Repository;
import spingboot.express.dto.WriteInfoDto;
import spingboot.express.dto.CancelOrderDto;
import spingboot.express.pojo.OrderInfo;

import java.util.List;

@Repository
//@Mapper
public interface OrderMapper {
    /**
     * function:发单人填写发单信息的接口
     * @param orderInfo
     */
     int add(OrderInfo orderInfo);

    /**
     * 查询所有订单接口
     * @return
     */
     List<OrderInfo> findAll();

    /**
     * 查询所有有效订单接口
     * @return
     */
    List<OrderInfo> findAllValid();

    /**
     * 接单用户接单pojo
     * @param orderInfo
     * @return
     */
     int userOrder(OrderInfo orderInfo);

    /**
     * 查找用户的发单列表
     * @param sendUserID
     * @return
     */
    List<OrderInfo> findSenderInfo(String sendUserID);

    /**
     * 查找用户的接单列表
     * @param sendUserID
     * @return
     */
    List<OrderInfo> findReceivedInfo(String sendUserID);

    /**
     * 检查订单是否已被其他人接收
     * @param ID
     * @return
     */
    String checkIfReceived(int ID);

    /**
     * 检查订单是否处于未被别人接收状态
     * @param ID
     * @return
     */
    int checkStatusById(int ID);

    /**
     * 根据订单ID查询订单的所有信息
     * @param ID
     * @return
     */
    OrderInfo checkInfoById(int ID);

    /**
     * 改变订单状态
     * @param orderInfo
     * @return
     */
    int changeOrderStatus(OrderInfo orderInfo);

    /**
     * 查询某条订单
     * @param ID
     * @return
     */
    List<OrderInfo> querySingleInfoOrder(int ID);

    /**
     * 发单用户取消发单，删除订单
     * @param ID
     * @return
     */
     int deleteInfo(int ID);

    /**
     * 更新操作，在删除主键后使主键连续
     * @param id
     * @return
     */
     int updatePrimary(int id);

    /**
     * 在添加主键时使主键连续
     * @param i
     * @return
     */
     int alterinfo(int i);

    /**
     * 接单用户取消接单,删除接单记录
     * @param ID
     */
     void deleteReceiver(int ID);

    /**
     *发单人编辑订单
     * @param writeInfoDto
     * @return
     */
     int senderEditInfo(WriteInfoDto writeInfoDto);

    /**
     * 接单人编辑订单
     * @param orderInfo
     * @return
     */
     int geterEditInfo(OrderInfo orderInfo);

    /**
     * 改变订单有效性
     * @param id 表示数据变化影响的行数
     * @return
     */
     int setToInvalid(int id);

    /**
     * 用户长时间未点击确认，自动确认
     * @param id
     * @return
     */
     int updateOrderStatusById(int id);

}

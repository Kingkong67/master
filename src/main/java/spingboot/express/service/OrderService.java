package spingboot.express.service;
/**
 * 信息模块接口
 */

import spingboot.express.dto.ReceiveOrderDto;
import spingboot.express.dto.WriteInfoDto;
import spingboot.express.dto.cancelOrderDto;
import spingboot.express.pojo.OrderInfo;

import java.util.HashMap;
import java.util.List;

public interface OrderService {

    /**
     * function:添加用户发单信息接口
     * @param orderInfo
     * @throws Exception
     */
     int add(OrderInfo orderInfo) throws Exception;

    /**
     * 查询所有用户发单信息接口
     * @return
     */
     List<OrderInfo> findAll() throws Exception;

    /**
     * 插叙所有有效订单借口
     * @return
     * @throws Exception
     */
    List<OrderInfo> findAllValid() throws Exception;

    /**
     * 用户接单接口
     * @param receiveOrderDto
     * @param ID
     * @return
     * @throws Exception
     */
     int userOrder(ReceiveOrderDto receiveOrderDto, Integer ID) throws Exception;


    /**
     * 查找用户发过的所有订单接口
     * @param sendUserID
     * @return
     * @throws Exception
     */
     List<OrderInfo> findSenderInfo(String sendUserID) throws Exception;

    /**
     * 查找用户接过的所有订单接口
     * @param receiverID
     * @return
     * @throws Exception
     */
     List<OrderInfo> findReceivedInfo(String receiverID) throws Exception;

    /**
     * 获取订单是否被接收
     * @param ID
     * @return
     * @throws Exception
     */
     String checkIfReceived(int ID) throws Exception;

     OrderInfo checkInfoById(int ID) throws Exception;


    /**
     * 检查订单的状态
     * @param ID
     * @return
     * @throws Exception
     */
     int checkStatus(int ID) throws Exception;

    /**
     * 改变订单状态
     * @param writeInfoDto
     * @param orderStatusID
     * @return
     * @throws Exception
     */
     int changeOrderStatus(WriteInfoDto writeInfoDto,int orderStatusID) throws Exception;

    /**
     * 删除某条订单
     * @param cancelOrderDto
     * @return
     */
     int deleteInfo(cancelOrderDto cancelOrderDto) throws Exception;

    /**
     * 更新操作，使数据库主键连续接口
     * @param paramMap
     * @return
     */
     int updatePrimary(HashMap<String,Object> paramMap) throws Exception;


//     orderInfo check(HashMap<String,Object> paramMap);

    /**
     * 使数据库主键连续接口
     * @param i
     * @return
     */
     int alter(int i) throws Exception;

    /**
     * 用户取消接单接口
     * @param writeInfoDto
     */
     void deleteReceiver(WriteInfoDto writeInfoDto) throws Exception;

    /**
     * 查询单个信息
     * @param paramMap
     * @return
     */
     List<OrderInfo> querySingleOrder(HashMap<String,Object> paramMap) throws Exception;

    /**
     * 发单人编辑单个信息
     * @param paramMap
     * @return
     */
     int senderEdit(HashMap<String,Object> paramMap) throws Exception;

    /**
     * 接单人编辑单个信息
     * @param paramMap
     * @return
     * @throws Exception
     */
     int geterEdit(HashMap<String,Object> paramMap) throws Exception;

    /**
     * 改变订单的有效性,失效之后不再展示在页面
     * @param id
     * @return
     */
     int isValid(int id) throws Exception;

    /**
     * 用户长时间未点击确认，自动确认
     * @param id
     */
     void updateOrderStatusById(int id) throws Exception;

}



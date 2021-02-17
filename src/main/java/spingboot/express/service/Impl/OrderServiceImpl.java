package spingboot.express.service.Impl;
/**
 * 信息模块实现类
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spingboot.express.dto.ReceiveOrderDto;
import spingboot.express.dto.WriteInfoDto;
import spingboot.express.dto.CancelOrderDto;
import spingboot.express.enums.OrderTypeEnum;
import spingboot.express.mapper.OrderMapper;
import spingboot.express.pojo.OrderInfo;
import spingboot.express.service.OrderService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 向数据库中添加快递的详尽信息,发单
     *
     * @param orderInfo
     * @throws Exception
     */
    @Override
//    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int add(OrderInfo orderInfo) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/*
        orderInfo.setSenderID(Integer.valueOf(paramMap.get("senderID").toString()));
        orderInfo.setTel(paramMap.get("tel").toString());
        System.out.println(paramMap.get("deadLine").toString());
        orderInfo.setDeadLine(simpleDateFormat.parse(paramMap.get("deadLine").toString()));
        orderInfo.setDeadLine(new Date());
        orderInfo.setMoney(paramMap.get("money").toString());
        orderInfo.setNote(paramMap.get("note").toString());
        orderInfo.setSex(paramMap.get("sex").toString());
        orderInfo.setSize(paramMap.get("size").toString());
        orderInfo.setShipAddress(paramMap.get("shipAddress").toString());
        orderInfo.setSendAddress(paramMap.get("sendAddress").toString());*/
        orderMapper.add(orderInfo);
        return orderInfo.getID();
    }


    /**
     * 查找用户发过的所有订单接口
     *
     * @param sendUserID
     * @return
     * @throws Exception
     */
    @Override
    public List<OrderInfo> findSenderInfo(String sendUserID) throws Exception {

        return orderMapper.findSenderInfo(sendUserID);
    }

    /**
     * 查找用户接过的所有订单接口
     *
     * @param receiverID
     * @return
     * @throws Exception
     */
    @Override
    public List<OrderInfo> findReceivedInfo(String receiverID) throws Exception {

        return orderMapper.findReceivedInfo(receiverID);
    }

    /**
     * 查询所有订单列表
     *
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<OrderInfo> findAll() throws Exception {

        return orderMapper.findAll();

    }

    /**
     * 插叙所有有效订单借口
     * @return
     * @throws Exception
     */
    @Override
    public List<OrderInfo> findAllValid() throws Exception {
        return orderMapper.findAllValid();
    }

    /**
     * 用户开始接单
     * @param receiveOrderDto
     * @return
     * @throws Exception
     */
    @Override
    public int userOrder(ReceiveOrderDto receiveOrderDto) throws Exception {
        receiveOrderDto.setOrderStatus(OrderTypeEnum.RECEIVED.getCode());
        ArrayList<ReceiveOrderDto> list = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        return orderMapper.userOrder(receiveOrderDto);
    }

    /**
     * 检查订单状态
     *
     * @param ID
     * @return
     * @throws Exception
     */
    @Override
    public String checkIfReceived(int ID) throws Exception {

        return orderMapper.checkIfReceived(ID);
    }

    /**
     * 根据订单ID检查订单的所有信息
     * @param ID
     * @return
     * @throws Exception
     */
    @Override
    public OrderInfo checkInfoById(int ID) throws Exception {
        return orderMapper.checkInfoById(ID);
    }

    /**
     * 根据订单ID检查订单的状态
     * @param ID
     * @return
     * @throws Exception
     */
    @Override
    public int checkStatusById(int ID) throws Exception {
        return orderMapper.checkStatusById(ID);
    }

    /**
     * 发单用户取消发单，删除订单
     *
     * @param ID
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//    @Cacheable(value="common",key="#paramMap")
    public int deleteInfo(int ID) throws Exception {
//        OrderInfo orderInfo = new OrderInfo();
//        orderInfo.setID(Integer.valueOf(paramMap.get("orderInfoID").toString()));
//        orderInfo.setSenderID(paramMap.get("SendUserID").toString());
        return orderMapper.deleteInfo(ID);
    }

    /**
     * 更新订单，删除订单之后使主键连续
     *
     * @param paramMap
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int updatePrimary(HashMap<String, Object> paramMap) throws Exception {
        return orderMapper.updatePrimary(Integer.valueOf(paramMap.get("orderInfoID").toString()));
    }

    /**
     * 删除接单用户id,电话
     *
     * @param ID
     */
    @Override
    public void deleteReceiver(int ID) throws Exception {

        orderMapper.deleteReceiver(ID);
    }

    /**
     * 改变订单状态
     * @param ID
     * @param orderStatusID
     * @return
     * @throws Exception
     */
    @Override
    public int changeOrderStatus(int ID, int orderStatusID) throws Exception {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setID(ID);
        orderInfo.setOrderStatus(orderStatusID);
        return orderMapper.changeOrderStatus(orderInfo);
    }

    @Override
    public void deleteSenderInOrder(int ID) throws Exception {

        orderMapper.deleteSenderInOrder(ID);
    }

    /**
     * 从最大的一条数据下面开始加
     *
     * @param i
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int alterInfo(int i) throws Exception {

        return orderMapper.alterInfo(i);
    }

    /**
     * 查询单个信息详情
     *
     * @param ID
     * @return
     */
    @Override
    public List<OrderInfo> querySingleOrder(int ID) throws Exception {

        return orderMapper.querySingleInfoOrder(ID);
    }

    /**
     * 发单人编辑订单
     *
     * @param writeInfoDto
     * @return
     * @throws Exception
     */
    @Override
    public int senderEditInfo(WriteInfoDto writeInfoDto) throws Exception {

        return orderMapper.senderEditInfo(writeInfoDto);
    }

    /**
     * 接单人编辑订单
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public int receiverEditInfo(HashMap<String, Object> paramMap) throws Exception {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setID(Integer.valueOf(paramMap.get("orderInfoID").toString()));
        orderInfo.setReceiveTel(paramMap.get("receiver_telephone").toString());
        orderInfo.setReceiverID(paramMap.get("receiverID").toString());
        return orderMapper.receiverEditInfo(orderInfo);
    }

    /**
     * 订单完成，失效之后不再展示在页面
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public int setToInvalid(int id) throws Exception {

        return orderMapper.setToInvalid(id);
    }

    /**
     * 改变订单为有效
     * @param ID
     * @return
     */
    @Override
    public int setToValid(int ID) throws Exception {
        return orderMapper.setToValid(ID);
    }

    /**
     * 用户长时间未点击确认，自动确认
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void updateOrderStatusById(int id) throws Exception {
        orderMapper.updateOrderStatusById(id);
    }


}

package spingboot.express.service.Impl;
/**
 * 信息模块实现类
 */

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spingboot.express.commons.PageDomain;
import spingboot.express.enums.OrderTypeEnum;
import spingboot.express.mapper.OrderMapper;
import spingboot.express.pojo.OrderInfo;
import spingboot.express.service.OrderService;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

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
        orderMapper.addInfo(orderInfo);
        return orderInfo.getID();
    }


    /**
     * 查找用户发过的所有订单接口
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public List<OrderInfo> findsend(HashMap<String, Object> paramMap) throws Exception {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setSenderID(Integer.valueOf(paramMap.get("sendUserID").toString()));
        return orderMapper.findsendInfo(orderInfo);
    }

    /**
     * 查找用户接过的所有订单接口
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public List<OrderInfo> findget(HashMap<String, Object> paramMap) throws Exception {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setReceiverID(Integer.valueOf(paramMap.get("userID").toString()));
        return orderMapper.findgetInfo(orderInfo);
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

    @Override
    public List<OrderInfo> findAllValid() throws Exception {
        return orderMapper.findAllValid();
    }

    /**
     * 用户开始接单实现
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public int userOrder(HashMap<String, Object> paramMap) throws Exception {

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setID(Integer.valueOf(paramMap.get("orderInfoID").toString()));
        orderInfo.setReceiverID(Integer.valueOf(paramMap.get("receiverID").toString()));
        orderInfo.setReceiveTel(paramMap.get("receiver_telephone").toString());
        orderInfo.setOrderStatus(OrderTypeEnum.RECEIVED.getCode());
        return orderMapper.order(orderInfo);
    }

    /**
     * 检查订单状态
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public OrderInfo check(int id) throws Exception {

        return orderMapper.checkInfo(id);
    }

    /**
     * 发单用户取消发单，删除订单
     *
     * @param paramMap
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//    @Cacheable(value="common",key="#paramMap")
    public int deletesender(HashMap<String, Object> paramMap) throws Exception {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setID(Integer.valueOf(paramMap.get("orderInfoID").toString()));
        orderInfo.setSenderID(Integer.valueOf(paramMap.get("SendUserID").toString()));
        return orderMapper.deletesenderInfo(orderInfo);
    }

    /**
     * 更新订单，删除订单之后使主键连续
     *
     * @param paramMap
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int updateinfo(HashMap<String, Object> paramMap) throws Exception {
        return orderMapper.update(Integer.valueOf(paramMap.get("orderInfoID").toString()));
    }

    /**
     * 删除接单用户id,电话
     *
     * @param paramMap
     */
    @Override
    public void deletereceiver(HashMap<String, Object> paramMap) throws Exception {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setID(Integer.valueOf(paramMap.get("orderInfoID").toString()));
        orderMapper.deletereceiverInfo(orderInfo);
    }

    /**
     * 改变订单状态
     *
     * @param paramMap
     * @return
     */
    @Override
    public int changestatus(HashMap<String, Object> paramMap, int orderStatusID) throws Exception {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setID(Integer.valueOf(paramMap.get("orderInfoID").toString()));
        orderInfo.setOrderStatus(orderStatusID);
        return orderMapper.changeInfostatus(orderInfo);
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
    public int alter(int i) throws Exception {

        return orderMapper.alterinfo(i);
    }

    /**
     * 查询单个信息详情
     *
     * @param paramMap
     * @return
     */
    @Override
    public List<OrderInfo> querySingleOrder(HashMap<String, Object> paramMap) throws Exception {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setID(Integer.valueOf(paramMap.get("orderInfoID").toString()));
        return orderMapper.querySingleInfoOrder(orderInfo);
    }

    /**
     * 发单人编辑订单
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public int senderEdit(HashMap<String, Object> paramMap) throws Exception {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setID(Integer.valueOf(paramMap.get("orderInfoID").toString()));
        orderInfo.setTel(paramMap.get("telephone").toString());
        return orderMapper.senderEditInfo(orderInfo);
    }

    /**
     * 接单人编辑订单
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Override
    public int geterEdit(HashMap<String, Object> paramMap) throws Exception {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setID(Integer.valueOf(paramMap.get("orderInfoID").toString()));
        orderInfo.setReceiveTel(paramMap.get("receiver_telephone").toString());
        orderInfo.setReceiverID(Integer.valueOf(paramMap.get("receiverID").toString()));
        return orderMapper.geterEditInfo(orderInfo);
    }

    /**
     * 订单完成，失效之后不再展示在页面
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public int isValid(int id) throws Exception {

        return orderMapper.isValid(id);
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

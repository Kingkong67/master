package spingboot.express.service;
/**
 * 信息模块接口
 */

import spingboot.express.dto.WriteInfoDto;
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
     List<OrderInfo> findall() throws Exception;

    /**
     * 用户接单接口
     * @param paramMap
     * @return
     * @throws Exception
     */
     int userOrder(HashMap<String,Object> paramMap) throws Exception;


    /**
     * 查找用户发过的所有订单接口
     * @param paramMap
     * @return
     * @throws Exception
     */
     List<OrderInfo> findsend(HashMap<String,Object> paramMap) throws Exception;

    /**
     * 查找用户接过的所有订单接口
     * @param paramMap
     * @return
     * @throws Exception
     */
     List<OrderInfo> findget(HashMap<String,Object> paramMap) throws Exception;

    /**
     * 获取订单状态
     * @param id
     * @return
     * @throws Exception
     */
     OrderInfo check(int id) throws Exception;

    /**
     * 改变订单状态
     * @param id
     * @return
     * @throws Exception
     */

    /**
     * 改变订单状态
     * @param paramMap
     * @return
     * @throws Exception
     */
     int changestatus(HashMap<String,Object> paramMap,int orderStatusID) throws Exception;


    /**
     * 删除订单
     * @param paramMap
     * @return
     */
     int deletesender(HashMap<String,Object> paramMap) throws Exception;

    /**
     * 更新操作，使数据库主键连续接口
     * @param paramMap
     * @return
     */
     int updateinfo(HashMap<String,Object> paramMap) throws Exception;


//     orderInfo check(HashMap<String,Object> paramMap);

    /**
     * 使数据库主键连续接口
     * @param i
     * @return
     */
     int alter(int i) throws Exception;

    /**
     * 用户取消接单接口
     * @param paramMap
     */
     void deletereceiver(HashMap<String,Object> paramMap) throws Exception;

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
     * 改变订单的有效性，订单完成，失效之后不再展示在页面
     * @param id
     * @return
     */
     int changeisValid(int id) throws Exception;

    /**
     * 用户长时间未点击确认，自动确认
     * @param id
     */
     void updateorderstatusbyid(int id) throws Exception;

}


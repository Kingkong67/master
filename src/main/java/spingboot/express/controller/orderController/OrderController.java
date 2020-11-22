package spingboot.express.controller.orderController;
/**
 * 订单模块控制器
 */

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spingboot.express.commons.Result;
import spingboot.express.enums.OrderTypeEnum;
import spingboot.express.pojo.OrderInfo;
import spingboot.express.pojo.User;
import spingboot.express.service.OrderService;
import spingboot.express.service.UserService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/orderInfo")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    /**
     * 注入用户接口
     */
    @Autowired
    private UserService userService;

    /**
     * 注入订单接口
     */
    @Autowired
    private OrderService orderService;

    /**
     *@描述 发单添加详细信息
     *@参数 [paramMap]
     *@返回值 spingboot.express.commons.Result
     *@创建人 wanghu
     *@创建时间 2020/11/22 5:22 上午
     *@修改人和其它信息
     */
    @PostMapping("/writeInfo")
    public Result writeInfo(@RequestBody HashMap<String, Object> paramMap) {
        Result result = new Result();
        try {
            logger.info("【发单用户填写信息开始】 writeInfo start");
            orderService.add(paramMap);
            result.setIsSuccess(true);
            logger.info("【增加订单信息成功】 writeInfo success");
            return result;
        } catch (Exception e) {
            result.setIsSuccess(false);
            logger.error("【增加订单信息失败】 writeInfo fail", e);
            return result;
        }
    }

    /**
     * 展示所有用户的有效订单信息
     *
     * @param paramMap
     * @return
     */
    @GetMapping("/list")
    public Result showAllOrderInfoList(@RequestBody HashMap<String, Object> paramMap) {
        Result result = new Result();
        try {
            /**
             * 对于超过24小时仍未点击确认的订单，系统自动确认
             */
            List<OrderInfo> k = orderService.findall();
            for (int i = 0; i < k.size(); i++) {
                OrderInfo orderInfo = k.get(i);
                if ((new Date().getTime()) - 86400000 > orderInfo.getDeadLine().getTime()) {
                    logger.info("开始自动帮助发单确认收到订单");
                    orderService.updateorderstatusbyid(orderInfo.getId());
                    logger.info("给接单用户酬金");
                    orderService.changeisValid(orderInfo.getId());
                }
            }
            logger.info("【查询所有订单列表开始】 showAllOrderInfoList start");
            PageHelper.startPage(Integer.valueOf(paramMap.get("pageCount").toString()),
                    Integer.valueOf(paramMap.get("countPerPage").toString()));
            List<OrderInfo> list = orderService.findall();
            PageInfo pageInfo = new PageInfo(list, 10);

            result.setIsSuccess(true);
            logger.info("【查询所有订单成功】 showAllOrderInfoList success");
            result.setData(pageInfo);
            return result;
        } catch (Exception e) {
            result.setIsSuccess(false);
            logger.error("【查询订单失败】 showAllOrderInfoList fail", e);
            return result;
        }
    }



    /**
     * 接单人接单，并添加接单人的电话信息,ID
     *
     * @param paramMap
     * @return
     */
    @PostMapping("/handleOrder")
    public Result handleOrder(@RequestBody HashMap<String, Object> paramMap) {
        Result result = new Result();

        try {
            logger.info("【检查接单用户信息是否完整】 viewIfFullUserInformation start");
            User user = userService.viewIfFullUserInformation(paramMap);
            if (user.getId_card() != null) {
                logger.info("【接单人开始接单】 handleOrder start");
                orderService.userOrder(paramMap);
                result.setIsSuccess(true);
                logger.info("【接单人接单成功】 handleOrder success");
                return result;
            } else {
                logger.error("【接单人信息未完善】 handleOrder fail");
                result.setIsSuccess(false);
                return result;
            }
        } catch (Exception e) {
            result.setIsSuccess(false);
            logger.error("【接单人接单失败】 handleOrder fail", e);
            return result;
        }
    }

    int CurrentNumber = 0;

    public void Choose(HashMap<String, Object> paramMap) {

        if (CurrentNumber == 0) {
            ExecutorService service = Executors.newCachedThreadPool();
            Date date = new Date();
        }
    }
    /**
     * 展示用户所有发单信息
     *
     * @param paramMap
     * @return
     */
    @PostMapping("/showAllUserSendOrdersList")
    public Result showAllUserSendOrdersList(@RequestBody HashMap<String, Object> paramMap) {
        Result result = new Result();
        try {
            logger.info("【查询用户所有发单信息开始】 showAllUserSendOrdersList start");
            PageHelper.startPage(Integer.valueOf(paramMap.get("pageNo").toString()),
                    Integer.valueOf(paramMap.get("pageSize").toString()));
//            用户发单信息
            List<OrderInfo> list = orderService.findsend(paramMap);
            PageInfo pageInfo = new PageInfo(list, 10);
            result.setData(pageInfo);
            result.setIsSuccess(true);
            logger.info("【查询用户所有发单信息成功】 showAllUserSendOrdersList success");
            return result;
        } catch (Exception e) {
            result.setIsSuccess(false);
            logger.error("【查询用户所有发单信息失败】 showAllUserSendOrdersList fail", e);
            return result;
        }
    }

    /**
     * 展示用户所有接单信息
     *
     * @param paramMap
     * @return
     */
    @PostMapping("/showAllUserGetOrdersList")
    public Result showAllUserGetOrdersList(@RequestBody HashMap<String, Object> paramMap) {
        Result result = new Result();
        try {
            logger.info("【查询用户所有接单信息开始】 showAllUserGetOrdersList start");
            PageHelper.startPage(Integer.valueOf(paramMap.get("pageNo").toString()),
                    Integer.valueOf(paramMap.get("pageSize").toString()));
//            用户接单信息
            List<OrderInfo> list = orderService.findget(paramMap);
            PageInfo pageInfo = new PageInfo(list, 10);
            result.setData(pageInfo);
            result.setIsSuccess(true);
            logger.info("【查询用户所有接单信息成功】 showAllUserGetOrdersList success");
            return result;
        } catch (Exception e) {
            result.setIsSuccess(false);
            logger.error("【查询用户所有接单信息失败】 showAllUserGetOrdersList fail", e);
            return result;
        }

    }

    /**
     * 发单人取消发单
     *
     * @param paramMap
     * @return
     */
    @RequestMapping("/cancleTheOrder")
    public Result cancleTheOrder(@RequestBody HashMap<String, Object> paramMap) {
        Result result = new Result();
        try {
            logger.info("【发单人取消发单开始】 cancleTheOrder start");
            OrderInfo orderInfo = orderService.check(Integer.valueOf(paramMap.get("orderInfoID").toString()));
            int ret = orderInfo.getOrderStatus();
//              在接单用户确认订单之前方可取消发单
            if (ret < OrderTypeEnum.RECEIVER_RECEIVED_THE_ORDER.getCode()) {
                orderService.deletesender(paramMap);
                orderService.updateinfo(paramMap);
                result.setIsSuccess(true);
                logger.info("【发单人取消发单成功】 cancleTheOrder success");
                return result;
            } else {
                result.setIsSuccess(false);
                result.setMessage("接单用户已确认拿到订单，无法取消");
                logger.info("【发单人取消发单失败】 cancleTheOrder fail");
                return result;
            }
        } catch (Exception e) {
            result.setIsSuccess(false);
            logger.error("【发单人取消发单失败】 cancleTheOrder fail", e);
            return result;
        }
    }

    /**
     * 接单人取消接单
     *
     * @param paramMap
     * @return 1800000 30分钟的getTime
     */
    @RequestMapping("/cancleReceipt")
    public Result cancleReceipt(@RequestBody HashMap<String, Object> paramMap) {
        Result result = new Result();
        try {
            OrderInfo orderInfo = orderService.check(Integer.valueOf(paramMap.get("orderInfoID").toString()));
            int ret = orderInfo.getOrderStatus();
            if (ret < OrderTypeEnum.RECEIVER_RECEIVED_THE_ORDER.getCode()) {

                if (new Date().getTime() - orderInfo.getReceiveTime().getTime() <
                        (orderInfo.getDeadLine().getTime() - orderInfo.getCreateTime().getTime()) / 4) {

                    logger.info("【接单人取消接单开始】 cancleReceipt start");
                    orderService.deletereceiver(paramMap);
                    orderService.changestatus(paramMap, OrderTypeEnum.NOT_RECEIVED.getCode());
                    result.setIsSuccess(true);
                    logger.info("【接单人取消接单成功】 cancleReceipt success");
                    return result;
                } else {
                    if (new Date().getTime() > orderInfo.getDeadLine().getTime()) {
                        logger.error("【惩罚100%】");
                        result.setIsSuccess(false);
                        result.setMessage("期限已过");
                        return result;
                    } else {
                        logger.info("【接单人取消接单开始】 cancleReceipt start");
                        orderService.deletereceiver(paramMap);
                        orderService.changestatus(paramMap, OrderTypeEnum.NOT_RECEIVED.getCode());
                        logger.info("【惩罚50%】");
                        result.setIsSuccess(true);
                        logger.info("【接单人取消接单成功】 cancleReceipt success");
                        return result;
                    }
                }
            } else {
                result.setIsSuccess(false);
                result.setMessage("接单用户已确认拿到订单，无法取消");
                logger.info("【接单人取消接单失败】 cancleReceipt fail");
                return result;
            }
        } catch (Exception e) {
            result.setIsSuccess(false);
            logger.error("【接单人取消接单失败】 cancleReceipt fail");
            return result;
        }

    }

    /**
     * 接单用户拿到订单
     *
     * @param paramMap
     * @return
     */
    @RequestMapping("/getOrder")
    public Result getOrder(@RequestBody HashMap<String, Object> paramMap) {
        Result result = new Result();
        try {
            logger.info("【接单人拿到订单开始】 getOrder start");
            orderService.changestatus(paramMap, OrderTypeEnum.RECEIVER_RECEIVED_THE_ORDER.getCode());
            orderService.changeisValid(Integer.valueOf(paramMap.get("orderInfoID").toString()));
            result.setIsSuccess(true);
            logger.info("【接单人拿到订单成功】 getOrder success");
            return result;
        } catch (Exception e) {
            result.setIsSuccess(false);
            logger.error("【接单人拿到订单失败】 getOrder fail");
            return result;
        }
    }

    /**
     * 接单用户点击完成订单
     *
     * @return
     */
    @RequestMapping("/completeOrder")
    public Result completeOrder(@RequestBody HashMap<String, Object> paramMap) {
        Result result = new Result();
        try {
            logger.info("【接单人完成订单开始】 completeOrder start");

            orderService.changestatus(paramMap, OrderTypeEnum.RECEIVER_CONFIRM_DELIVERY.getCode());
            result.setMessage(OrderTypeEnum.RECEIVER_CONFIRM_DELIVERY.getName());
            result.setIsSuccess(true);
            logger.info("【接单人完成订单成功】 completeOrder success");
            return result;
        } catch (Exception e) {
            result.setIsSuccess(false);
            logger.error("【接单人完成订单失败】 completeOrder fail", e);
            return result;
        }
    }

    /**
     * 查询单个信息详情
     *
     * @param paramMap
     * @return
     */
    @RequestMapping("/showUserOrderInfo")
    public Result showUserOrderInfo(@RequestBody HashMap<String, Object> paramMap) {
        Result result = new Result();
        try {
            logger.info("【查询单个订单详情开始】 showUserOrderInfo start");
            List<OrderInfo> list = orderService.querySingleOrder(paramMap);
            result.setData(list);
            result.setIsSuccess(true);
            logger.info("【查询单个订单详情成功】 showUserOrderInfo success");
            return result;
        } catch (Exception e) {
            result.setIsSuccess(false);
            logger.error("【查询单个订单详情失败】 showUserOrderInfo fail", e);
            return result;
        }
    }

    /**
     * 删除某条发单信息
     *
     * @param paramMap
     * @return
     */
    @RequestMapping("/deleteSendOrder")
    public Result deleteSendOrder(@RequestBody HashMap<String, Object> paramMap) {
        Result result = new Result();
        try {
            OrderInfo orderInfo = orderService.check(Integer.valueOf(paramMap.get("orderInfoID").toString()));
            int ret = orderInfo.getOrderStatus();
            if (ret > OrderTypeEnum.COMPLETED.getCode()) {
                logger.info("【删除发单信息开始】 deleteSendOrder start");
                orderService.deletesender(paramMap);
                result.setIsSuccess(true);
                logger.info("【删除发单信息成功】 deleteSendOrder success");
                return result;
            } else {
                result.setMessage("暂时无权删除订单");
                result.setIsSuccess(false);
                return result;
            }
        } catch (Exception e) {
            result.setIsSuccess(false);
            logger.error("【删除发单信息失败】 deleteSendOrder fail", e);
            return result;
        }
    }

    /**
     * 删除某条接单信息
     *
     * @param paramMap
     * @return
     */
    @RequestMapping("/deleteGetOrder")
    public Result deleteGetOrder(@RequestBody HashMap<String, Object> paramMap) {
        Result result = new Result();
        try {
            OrderInfo orderInfo = orderService.check(Integer.valueOf(paramMap.get("orderInfoID").toString()));
            int ret = orderInfo.getOrderStatus();
            if (ret > OrderTypeEnum.COMPLETED.getCode()) {
                logger.info("【删除接单信息开始】 deleteGetOrder start");
                orderService.deletereceiver(paramMap);
                result.setIsSuccess(true);
                logger.info("【删除接单信息成功】 deleteGetOrder success");
                return result;
            } else {
                result.setMessage("无权删除订单");
                result.setIsSuccess(false);
                return result;
            }
        } catch (Exception e) {
            result.setIsSuccess(false);
            logger.error("【删除接单信息失败】 deleteGetOrder fail", e);
            return result;
        }
    }

    /**
     * 发单人编辑某条订单信息
     *
     * @param paramMap
     * @return
     */
    @RequestMapping("/senderEditOrder")
    public Result senderEditOrder(@RequestBody HashMap<String, Object> paramMap) {
        Result result = new Result();
        try {
            logger.info("【编辑订单信息开始】 editOrder start");
            orderService.senderEdit(paramMap);
            result.setIsSuccess(true);
            logger.info("【编辑订单信息成功】 editOrder success");
            return result;
        } catch (Exception e) {
            result.setIsSuccess(false);
            logger.error("【编辑订单信息失败】 editOrder fail", e);
            return result;
        }
    }

    /**
     * 发单人确认收到订单
     *
     * @param paramMap
     * @return
     */
    @RequestMapping("/confirmReceipt")
    public Result confirmReceipt(@RequestBody HashMap<String, Object> paramMap) {
        Result result = new Result();
        try {
            logger.info("【发单人确认订单开始】 confirmReceipt start");
            orderService.changestatus(paramMap, OrderTypeEnum.CONFIRMED.getCode());
            orderService.changeisValid(Integer.valueOf(paramMap.get("orderInfoID").toString()));
            result.setIsSuccess(true);
            logger.info("【发单人确认订单成功】 confirmReceipt success");
            logger.info("【平台付费给用户】");
            return result;
        } catch (Exception e) {
            result.setIsSuccess(false);
            logger.info("【发单人确认订单失败】 confirmReceipt fail", e);
            return result;
        }
    }

    /**
     * 发单人点击未收到订单
     *
     * @param paramMap
     * @return
     */
    @RequestMapping("/noOrdersReceived")
    public Result noOrdersReceived(@RequestBody HashMap<String, Object> paramMap) {
        Result result = new Result();
        try {
            logger.info("【发单人点击未确认订单开始】 noOrdersReceived start");
            OrderInfo orderInfo = orderService.check(Integer.valueOf(paramMap.get("orderInfoID").toString()));
            int ret = orderInfo.getOrderStatus();
            if (ret == OrderTypeEnum.RECEIVER_CONFIRM_DELIVERY.getCode()) {
                logger.info("【平台介入调查】");
                result.setIsSuccess(false);
                return result;
            } else {
                orderService.changestatus(paramMap, OrderTypeEnum.NOT_CONFIRMED.getCode());
                orderService.changeisValid(Integer.valueOf(paramMap.get("orderInfoID").toString()));
                result.setIsSuccess(true);
                logger.info("【发单人点击未确认订单成功】 noOrdersReceived success");
                return result;
            }

        } catch (Exception e) {
            result.setIsSuccess(false);
            logger.info("【发单人点击未确认订单失败】 noOrdersReceived fail", e);
            return result;
        }
    }
}

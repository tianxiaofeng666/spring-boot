package com.example.springbootdelaytask.mapper;

import com.example.springbootdelaytask.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author shs-cyhlwzytxf
 */
public interface OrderMapper {

    /**
     * 创建订单
     * @param order
     */
    @Insert("insert into t_order(order_id,mobile,email,create_user) values(#{orderId},#{mobile},#{email},#{createUser})")
    void createOrder(Order order);

    /**
     * 支付
     * @param orderId
     */
    @Update("update t_order set status = 1, payment_time = now() where order_id = #{orderId}")
    void paymentOrder(String orderId);

    /**
     * 通过orderId查询order
     * @param orderId
     * @return
     */
    @Select("select * from t_order where order_id = #{orderId}")
    Order getOrderByOrderId(String orderId);

    /**
     * 通过orderId删除order
     * @param orderId
     */
    @Update("update t_order set modified_time = now(), is_deleted = 1 where order_id = #{orderId} ")
    void deleteOrderByOrderId(String orderId);
}

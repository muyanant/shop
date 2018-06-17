package shop.service;

import shop.dao.OrderDao;
import shop.dao.OrderItemDao;
import shop.dao.ProductDao;
import shop.domain.Order;
import shop.domain.OrderItem;
import shop.domain.User;
import utils.DataSourceUtil;

import java.sql.SQLException;
import java.util.List;



public class OrderService {

    // 添加订单方法
    public void addOrder(Order order) {
        OrderDao odao = new OrderDao();
        OrderItemDao oidao = new OrderItemDao();
        ProductDao pdao = new ProductDao();

        // 开启事务
        try {
            DataSourceUtil.startTransaction(DataSourceUtil
                    .getConnectionByTransaction());
            // 1.调用OrderDao完成向orders表中添加数据
            odao.addOrder(order);
            // 2.调用OrderItemDao完成对orderItem表的添加操作
            oidao.addOrderItem(order.getItems());
            // 3.调用ProductDao完成对products表中数量修改操作.
            pdao.updatePnum(order.getItems());
        } catch (SQLException e) {
            e.printStackTrace();
            // 事务回滚
            try {
                DataSourceUtil.rollback(DataSourceUtil
                        .getConnectionByTransaction());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            // 释放资源,并且事务提交以及从ThreadLocal中移除Connection。
            try {
                DataSourceUtil.closeConnection(DataSourceUtil
                        .getConnectionByTransaction());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    // 根据用户查询订单
    public List<Order> findOrder(User user) throws SQLException {
        return new OrderDao().findOrder(user);
    }

    // 删除订单
    public void delOrderById(String id) {

        OrderItemDao oidao = new OrderItemDao();
        OrderDao odao = new OrderDao();
        ProductDao pdao = new ProductDao();

        try {
            // 1.开启事务
            DataSourceUtil.startTransaction(DataSourceUtil
                    .getConnectionByTransaction());
            // 2.操作
            // 2.1 根据orderid查询出所有订单项
            List<OrderItem> items = oidao.findOrderItemByOrderId(id);
            // 2.2根据orderid删除相关订单项
            oidao.delOrderItemByOrderId(id);
            // 2.3根据orderid删除订单
            odao.delOrderById(id);
            // 2.4.根据查询出的items，修改products表中的数据.
            pdao.changePnum(items);

        } catch (Exception e) {
            try {
                DataSourceUtil.rollback(DataSourceUtil
                        .getConnectionByTransaction());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        } finally {
            try {
                DataSourceUtil.closeConnection(DataSourceUtil
                        .getConnectionByTransaction());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }
}

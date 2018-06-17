package shop.service;



import shop.dao.OrderItemDao;
import shop.domain.Product;

import java.sql.SQLException;
import java.util.List;



public class OrderItemService {

    //根据订单号查询出订单中商品信息
    public List<Product> findProductByOrderId(String orderid) throws SQLException {
        return new OrderItemDao().findProductByOrderId(orderid);
    }



}

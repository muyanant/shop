package shop.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import shop.domain.OrderItem;
import shop.domain.Product;
import utils.DataSourceUtil;


public class OrderItemDao {
    // 添加订单项
    public void addOrderItem(List<OrderItem> items) throws SQLException {
        String sql = "insert into orderItem values(?,?,?)";

        QueryRunner runner = new QueryRunner();
        Object[][] params = new Object[items.size()][3];

        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            params[i][0] = item.getOrder_id();
            params[i][1] = item.getProduct_id();
            params[i][2] = item.getBuynum();
        }

        runner.batch(DataSourceUtil.getConnectionByTransaction(), sql, params);
    }

    public List<Product> findProductByOrderId(String orderid)
            throws SQLException {
        String sql = "select p.name,p.price,p.description,p.imgurl from products p,orderitem oi where p.id=oi.product_id and oi.order_id=?";

        QueryRunner runner = new QueryRunner(DataSourceUtil.getDataSources());

        return runner.query(sql, new BeanListHandler<Product>(Product.class),
                orderid);
    }

    // 根据订单id查询所有的订单项.
    public List<OrderItem> findOrderItemByOrderId(String id)
            throws SQLException {
        String sql = "select * from orderitem where order_id=?";
        QueryRunner runner = new QueryRunner();

        return runner.query(DataSourceUtil.getConnectionByTransaction(), sql,
                new BeanListHandler<OrderItem>(OrderItem.class), id);
    }

    // 根据订单id删除订单项
    public void delOrderItemByOrderId(String id) throws SQLException {
        String sql = "delete  from orderitem where order_id=?";
        QueryRunner runner = new QueryRunner();

        runner.update(DataSourceUtil.getConnectionByTransaction(), sql, id);
    }

}

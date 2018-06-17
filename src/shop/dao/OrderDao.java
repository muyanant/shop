package shop.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import shop.domain.Order;
import shop.domain.User;
import utils.DataSourceUtil;


public class OrderDao {
    // 添加订单
    public void addOrder(Order order) throws SQLException {
        String sql = "insert into orders values(?,?,?,?,null,?)";

        QueryRunner runner = new QueryRunner(); // 手动事务

        runner.update(DataSourceUtil.getConnectionByTransaction(), sql,
                order.getId(), order.getMoney(), order.getReceiverinfo(),
                order.getPaystate(), order.getUser_id());
    }

    // 根据用户的角色来查询订单
    public List<Order> findOrder(User user) throws SQLException {

        String sql = "select users.username,users.nickname,orders.* from orders,users where users.id=orders.user_id ";
        //如果只是user的话只能看自己订单
        if ("user".equals(user.getRole())) {

            sql += " and user_id=" + user.getId();
        }
        //admin的话可以查看全部订单
        sql += " order by user_id,ordertime";

        QueryRunner runner = new QueryRunner(DataSourceUtil.getDataSources());
        return runner.query(sql, new BeanListHandler<Order>(Order.class));
    }

    // 根据id删除订单
    public void delOrderById(String id) throws SQLException {
        String sql = "delete  from orders where id=?";
        QueryRunner runner = new QueryRunner();

        runner.update(DataSourceUtil.getConnectionByTransaction(), sql, id);
    }

}
package shop.dao;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;


import org.apache.commons.dbutils.handlers.ScalarHandler;
import shop.domain.OrderItem;
import shop.domain.Product;
import utils.DataSourceUtil;

public class ProductDao {
    // 添加商品
    public void addProduct(Product p) throws SQLException {
        String sql = "insert into products values(?,?,?,?,?,?,?)";

        QueryRunner runner = new QueryRunner(DataSourceUtil.getDataSources());

        runner.update(sql, p.getId(), p.getName(), p.getPrice(),
                p.getCategory(), p.getPnum(), p.getImgurl(), p.getDescription());
    }

    // 分页查询所有商品

    /**
     *
     * @param pageNum 页数
     * @param currentPage 一页的数量
     * @return
     * @throws SQLException
     */
    public List<Product> findAllByPage(int pageNum,int currentPage) throws SQLException {
        String sql = "select * from products limit ?,?";
        QueryRunner runner = new QueryRunner(DataSourceUtil.getDataSources());

        return runner.query(sql, new BeanListHandler<Product>(Product.class),(pageNum-1)*currentPage,currentPage);
    }

    public Product findById(String id) throws SQLException {
        String sql = "select * from products where id=?";
        QueryRunner runner = new QueryRunner(DataSourceUtil.getDataSources());

        return runner.query(sql, new BeanHandler<Product>(Product.class), id);
    }

    // 添加订单后，修改商品的数量.
    public void updatePnum(List<OrderItem> items) throws SQLException {
        String sql = "update products set pnum=pnum-? where id=?";

        QueryRunner runner = new QueryRunner();
        Object[][] params = new Object[items.size()][2];

        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            params[i][0] = item.getBuynum();
            params[i][1] = item.getProduct_id();

        }

        runner.batch(DataSourceUtil.getConnectionByTransaction(), sql, params);
    }

    // 删除订单后，恢复商品的数量
    public void changePnum(List<OrderItem> items) throws SQLException {
        String sql = "update products set pnum=pnum+? where id=?";

        QueryRunner runner = new QueryRunner();
        Object[][] params = new Object[items.size()][2];

        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            params[i][0] = item.getBuynum();
            params[i][1] = item.getProduct_id();

        }

        runner.batch(DataSourceUtil.getConnectionByTransaction(), sql, params);
    }

    // 下载榜单
    public List<Product> downloadSell() throws SQLException {
        String sql = "select products.name,sum(buynum) totalSaleNum			from					products,orderitem,orders 			where 				orderitem.product_id=products.id			and				orders.id=orderitem.order_id			and				orders.paystate=1			group by				products.name			order by				totalSaleNum desc";

        QueryRunner runner = new QueryRunner(DataSourceUtil.getDataSources());

        return runner.query(sql, new BeanListHandler<Product>(Product.class));
    }

    /**
     * 获得商品总体数量
     * @return
     */
    public int getTotalPage() throws SQLException {
        String sql = "select count(*) from products";
        QueryRunner qr = new QueryRunner(DataSourceUtil.getDataSources());
        long count = (Long)qr.query(sql,new ScalarHandler());
        return (int)count;
    }

    /**
     * 获得分类的集合
     * @param category
     * @return
     */
    public List<Product> findProductByCategory(String category) throws SQLException {
        QueryRunner query=new QueryRunner(DataSourceUtil.getDataSources());
        String sql="select * from products where category=?";
       return query.query(sql,new BeanListHandler<Product>(Product.class),category);
    }
}

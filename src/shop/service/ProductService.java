package shop.service;

import shop.dao.ProductDao;
import shop.domain.PageBean;
import shop.domain.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    //添加商品
    public void addProduct(Product p) throws SQLException {
        new ProductDao().addProduct(p);
    }



    //根据id查询商品
    public Product findById(String id) throws SQLException {
        return new ProductDao().findById(id);
    }


    public PageBean findAllByPage(int pageNum, int currentPage) throws SQLException {
        ProductDao dao=new ProductDao();
        //获得ps
        List<Product> ps = dao.findAllByPage(pageNum, currentPage);
        //获得总页数
        int totalCount=dao.getTotalPage();
        int totalPage=(int) Math.ceil(totalCount*1.0/currentPage);//获得总页数

        //封装
        PageBean pb=new PageBean();
        pb.setPageNum(pageNum);
        pb.setCurrentPage(currentPage);
        pb.setTotalPage(totalPage);
        pb.setTotalCount(totalCount);
        pb.setPs(ps);
        return  pb;
    }

    /**
     * 获得分类的业务
     * @param category
     * @return
     */
    public List<Product> findProductByCategory(String category) throws SQLException {
        ProductDao dao=new ProductDao();
        return dao.findProductByCategory(category);

    }
}


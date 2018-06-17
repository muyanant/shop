package shop.web;

import shop.domain.PageBean;
import shop.domain.Product;
import shop.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * 分页显示商品
 */
@WebServlet(name = "FindAllProductServlet",urlPatterns = "/allProductByPage")
public class FindAllProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            ProductService service=new ProductService();
         //默认访问第一页
            int pageNum=1;
            //默认页码数10
            int currentPage=10;
        //检测是否改变页数什么的
        String _pageNum=request.getParameter("pageNum");
        String _currentPage=request.getParameter("currentPage");
        if (_currentPage!=null){
            currentPage=Integer.parseInt(_currentPage.trim());
        }

        if (_pageNum!=null){
            pageNum=Integer.parseInt(_pageNum.trim());
        }

        try {
            PageBean pb = service.findAllByPage(pageNum,currentPage);

            request.setAttribute("pb",pb);
            request.getRequestDispatcher("/index.jsp").forward(request,response);
            return;
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("获取商品信息失败");
            response.getWriter().close();
        }
    }
}

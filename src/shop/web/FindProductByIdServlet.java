package shop.web;

import shop.domain.Product;
import shop.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "FindProductByIdServlet",urlPatterns = "/findProductById")
public class FindProductByIdServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id=request.getParameter("id");

        //获取对应id的商品
        ProductService service=new ProductService();
        try {
            Product p = service.findById(id);
            if (p!=null){
                request.setAttribute("p",p);
                request.getRequestDispatcher("/jsp/productionInfo.jsp").forward(request,response);
            }else {
                response.getWriter().write("没有该商品");
                response.getWriter().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("服务器获取失败，可能是智障了，静待医生抢救");
            response.getWriter().close();
        }

    }
}

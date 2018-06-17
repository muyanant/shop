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

@WebServlet(name = "FindProductByCategoryServlet",urlPatterns = "/findProductByCategory")
public class FindProductByCategoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String num=request.getParameter("num");
        String category;
        if (num.equals("1")){
            category="图书音像";
        }else  if (num.equals("2")){
            category="家用电器";
        }else  if (num.equals("3")){
            category="手机数码";
        }else  if (num.equals("4")){
            category="电脑办公";
        }else {
            category="服装衣帽";
        }
        ProductService service=new ProductService();
        //获得分类的分页javabean
        try {
            List<Product>categoryList=service.findProductByCategory(category);
            if (categoryList.size()!=0) {
                request.getSession().setAttribute("categoryList", categoryList);
                response.sendRedirect(request.getContextPath() + "/jsp/showProductByCategory.jsp");
            }
                else {
                response.getWriter().write("查询失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

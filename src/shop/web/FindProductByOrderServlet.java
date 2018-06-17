package shop.web;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import shop.domain.Product;
import shop.service.OrderItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "FindProductByOrderServlet",urlPatterns = "/findProductByOrder")
public class FindProductByOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.得到要查询的订单的id
        String orderid = request.getParameter("id");

        // 2.调用service,得到List<Product>
        OrderItemService service = new OrderItemService();
        try {
            List<Product> ps = service.findProductByOrderId(orderid);

           request.setAttribute("fps",ps);
           request.getRequestDispatcher("/showProduct.jsp").forward(request,response);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package shop.web;

import shop.domain.Order;
import shop.domain.User;
import shop.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ShowOrderServlet",urlPatterns = "/showOrder")
public class ShowOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.得到当前用户
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user!=null){
        // 2.调用OrderService完成查询订单操作
        OrderService service = new OrderService();
        try {
            List<Order> orders = service.findOrder(user);
            if (orders.size()!=0) {
                request.setAttribute("orders", orders);
                request.getRequestDispatcher("/jsp/showOrder.jsp").forward(request,
                        response);
                return;

            }else {
                response.getWriter().write("尊敬的"+user.getNickname()+"你还没有订单<a href="+request.getContextPath()+"/default.jsp>点击去买买买</a>");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }else {
            response.getWriter().write("用户没登录<a href="+request.getContextPath()+" 请点击登录</a>");
        }
    }
}

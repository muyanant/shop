package shop.web;

import shop.domain.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
@WebServlet(name = "ChangCountServlet",urlPatterns = "/changeCount")
public class ChangCountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.得到请求参数
        String id = request.getParameter("id");
        int count = Integer.parseInt(request.getParameter("count"));

        // 2.修改购物车中指定商品的数量.
        // 2.1得到购物车
        Map<Product, Integer> cart = (Map<Product, Integer>) request
                .getSession().getAttribute("cart");

        // 2.2.修改购物车中商品数量
        Product p = new Product();
        p.setId(id);

        if (count == 0) {
            // 删除商品
            cart.remove(p);
        } else {
            cart.put(p, count);
        }
        response.sendRedirect(request.getContextPath() + "/jsp/showCart.jsp");
    }
}

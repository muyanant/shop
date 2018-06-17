package shop.web;

import shop.domain.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 从购物车中删除单个商品
 */
@WebServlet(name = "RemoveProductFromCartServlet",urlPatterns = "/removeProductFromCart")
public class RemoveProductFromCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 得到要删除的商品的id
        String id = request.getParameter("id");
        // 得到购物车，从购物车中将商品删除,
        Map<Product, Integer> cart = (Map<Product, Integer>) request
                .getSession().getAttribute("cart");
        Product p = new Product();
        p.setId(id);
        cart.remove(p);

        //如果购物车中无商品，将购物车删除。
        if (cart.size() == 0) {
            request.getSession().removeAttribute("cart");
        }
        response.sendRedirect(request.getContextPath() + "/jsp/showCart.jsp");
    }
}

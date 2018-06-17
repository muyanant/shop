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
 * 删除选中的servlet
 */
@WebServlet(name = "RemoveSelectProductFromCartServlet",urlPatterns ="/removeSelectProductsFromCart" )
public class RemoveSelectProductFromCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] id = request.getParameterValues("id");

        Map<Product, Integer> cart = (Map<Product, Integer>) request
                .getSession().getAttribute("cart");

        for (int i = 0; i < id.length; i++) {
            Product p = new Product();
            p.setId(id[i]);
            cart.remove(p);
        }

        response.sendRedirect(request.getContextPath() + "/jsp/showCart.jsp");

    }
}

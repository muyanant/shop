package shop.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import shop.domain.Order;
import shop.domain.OrderItem;
import shop.domain.Product;
import shop.domain.User;
import shop.service.OrderService;


@WebServlet(name = "AddOrderServlet",urlPatterns = "/addOrder")
public class AddOrderServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.得到所有请求参数,封装到Order对象中
       Order order = new Order();
        try {
            // 它封装了订单的 送货地址,总价.
            BeanUtils.populate(order, request.getParameterMap());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // 2.手动封装一些信息
        String id = UUID.randomUUID().toString();
        order.setId(id);// 封装订单的id
        order.setPaystate(0);// 默认值为0,代表未支付。如果为1，代表支付.

        // 封装user_id
        // 从session中获取当前用户.
        User user = (User) request.getSession().getAttribute("loginUser");

        if (user!=null) {
            int user_id = user.getId();
            order.setUser_id(user_id);

            // 将订单中的所有的订单项信息封装。
            List<OrderItem> items = new ArrayList<OrderItem>();
            //得到购物车
            Map<Product, Integer> cart = (Map<Product, Integer>) request.getSession().getAttribute("cart");
            //遍历购物车
            for (Product p : cart.keySet()) {
                OrderItem item = new OrderItem(); //创建一个订单项

                item.setOrder_id(order.getId()); //向订单项中封装当前订单编号
                item.setProduct_id(p.getId()); //封装订单项中商品id
                item.setBuynum(cart.get(p));//封装订单项中的商品数量

                items.add(item);
            }

            order.setItems(items);

            //调用service中添加订单的方法
            OrderService service = new OrderService();

            service.addOrder(order);

            request.getSession().removeAttribute("cart");//生成订单后，将购物车中商品删除。

            response.getWriter().write("订单生成成功，<a href='" + request.getContextPath() + "/showOrder'>查看订单</a>");
        }else {
            response.getWriter().write("<a href="+request.getContextPath()+"/default.jsp>请先登录</a>");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}

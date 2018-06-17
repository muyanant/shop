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
import java.util.HashMap;
import java.util.Map;
/**
 * 添加购物车的servlet
 */
@WebServlet(name = "AddCartServlet",urlPatterns = "/addProductToCart")
public class AddCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
    /**
     * 思路：
     *  1先获得session 中的cart 并判断是否为空 要是为空则表示第一次访问
     *  第一次访问则需要新建一个Map<Product,Integer>对象
     *  然后把name count=1存进去
     *  不是为空则需要 判断name 是否在cart里面要是有的话 count+1；
     *   要是没有的话 把name count=1存进去
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id=request.getParameter("id");

        //先查询该商品
        ProductService service=new ProductService();
        try {
            Product product = service.findById(id);
            //使用Map<product ,number> 表示购物车
            Map<Product,Integer> cart= (Map<Product, Integer>) request.getSession().getAttribute("cart");

            //判断cart是否为空
            if (cart==null){
                cart=new HashMap<>();
                cart.put(product,1);
                request.getSession().setAttribute("cart",cart);
                response.sendRedirect(request.getContextPath()+"/jsp/addCartSuccess.jsp");
                return;
            }else {
                //判断购物车是否包含着产品
                if(cart.containsKey(product)){
                    int count=cart.get(product);
                    count++;;
                    cart.put(product,count);
                    request.getSession().setAttribute("cart",cart);
                    response.sendRedirect(request.getContextPath()+"/jsp/addCartSuccess.jsp");
                }else{
                    cart.put(product,1);
                    request.getSession().setAttribute("cart",cart);
                    response.sendRedirect(request.getContextPath()+"/jsp/addCartSuccess.jsp");
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("加入购物车失败 可能是服务器开小差了");
            response.getWriter().close();
        }
    }
}

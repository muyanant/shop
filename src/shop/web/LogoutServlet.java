package shop.web;

import shop.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 注销页面
 */
@WebServlet(name = "LogoutServlet",urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //先判断user是否为空
        User user= (User) request.getSession().getAttribute("loginUser");
        if (user!=null){
            request.getSession().removeAttribute("loginUser");
            response.sendRedirect(request.getContextPath()+"/default.jsp");
        }else {
            response.sendRedirect(request.getContextPath()+"/default.jsp");
        }
    }
}

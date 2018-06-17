package shop.web;

import shop.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ActiveServlet",urlPatterns = "/active")
public class ActiveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获得激活码
        String code=request.getParameter("ActiveCode");
        //通过激活码来查找user
        UserService service=new UserService();
        try {
            service.findUserByActiveCode(code);
            response.sendRedirect(request.getContextPath()+"/jsp/regSuccess.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

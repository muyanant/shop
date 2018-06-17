package shop.web;

import shop.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CheckUsernameServlet",urlPatterns = "/check1")
public class CheckUsernameServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        //获得用户名
        String username = request.getParameter("username");

        UserService service = new UserService();
        boolean isExist = false;
        try {
            isExist = service.checkUsername(username);
            if (isExist) {
                response.getWriter().write("<font color='red'>用户名已被使用</font>");
            }else{
                response.getWriter().write("<font color='green'>用户名可以使用</font>");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

package shop.web;

import shop.domain.User;
import shop.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet",urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获得账号密码
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String autoLogin=request.getParameter("autologin");

        //封装进user中
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);


        UserService service=new UserService();
        try {
            User loginUser=service.findUser(user);
            if (loginUser!=null){
                //检查是否点击了自动登录如果有则新建立一个autoLoginCookie 不则销毁该cookie
                if ("1".equals(autoLogin)){
                    Cookie autoLoginCookie=new Cookie("autoLoginCookie",user.getUsername()+"@"+user.getPassword());
                    autoLoginCookie.setPath("/");
                    autoLoginCookie.setMaxAge(60*60*24*7);//7天
                    response.addCookie(autoLoginCookie);
                }else {
                    Cookie autoLoginCookie=new Cookie("autoLoginCookie","");
                    autoLoginCookie.setPath("/");
                    autoLoginCookie.setMaxAge(0);
                    response.addCookie(autoLoginCookie);
                }

                //检查是否记住用户名
                String remember=request.getParameter("remember");
                if ("1".equals(remember)){
                    Cookie rememberCookie=new Cookie("rememberCookie",user.getUsername());
                    rememberCookie.setPath("/");
                    rememberCookie.setMaxAge(60*60*24*7);
                    response.addCookie(rememberCookie);
                }else {
                    Cookie rememberCookie=new Cookie("rememberCookie","");
                    rememberCookie.setPath("/");
                    rememberCookie.setMaxAge(0);
                    response.addCookie(rememberCookie);
                }
                request.getSession().invalidate();//先销毁session
                request.getSession().setAttribute("loginUser",loginUser);
                request.getRequestDispatcher("/default.jsp").forward(request,response);
            }else{
               response.getWriter().write("登录失败请检查用户名密码 <a href="+request.getContextPath()+"/default.jsp></>点击回到首页");
               response.getWriter().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("服务器故障");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

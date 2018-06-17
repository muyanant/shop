package filter;

import shop.domain.User;
import shop.service.UserService;
import utils.CookieUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;

@WebFilter(filterName = "AutoLoginFilter",urlPatterns = "/")
public class AutoLoginFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {

    }


    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        // 处理请求乱码
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        // 处理自动登录
        // 1.如果用户已经登录不需要自动登录
        User user= (User) request.getSession().getAttribute("loginUser");

        if (user==null){
            // 没有登录，进行自动登录
            // 2.判断访问的资源路径，例如登录，注册不需要进行自动登录
            String url=request.getRequestURI();
            String contextPath=request.getContextPath();
            String path=url.substring(contextPath.length());
            if(! ("/reg".equals(path)||"/jsp/reg.jsp".equals(path)|| "/login".equals(path)|| "/logout".equals(path))){
                // 3.得到Cookie
                Cookie cookie = CookieUtils.getCookie(
                       request.getCookies(), "autoLoginCookie");
                if (cookie != null) {

                    // 有cookie，进行登录操作.
                    String username = URLDecoder.decode(cookie.getValue()
                            .split("::")[0], "utf-8");
                    String password = cookie.getValue().split("::")[1];

                    UserService service = new UserService();
                    try {
                        User u =new User();
                        u.setUsername(username);
                        u.setPassword(password);
                        User user1=service.findUser(u);
                        if (user1!=null) {
                            request.getSession().setAttribute(
                                    "loginUser", user1);// 进行自动登录.
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }catch (RuntimeException e){
                        response.getWriter().write("自动登录故障");
                    }

                }

            }
        }

        // 放行
        chain.doFilter(request, response);
            }




    public void destroy() {
    }

}

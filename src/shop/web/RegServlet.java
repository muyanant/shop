package shop.web;

import org.apache.commons.beanutils.BeanUtils;
import shop.domain.User;
import shop.service.UserService;
import utils.CommonsUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

@WebServlet(name = "RegServlet",urlPatterns = "/reg")
public class RegServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        User user=new User();
        try {
            BeanUtils.populate(user,request.getParameterMap());
            //缺少的数据要自己封装
            //private int id; // 用户编号因为数据库设为自动增长可以不加

            //private String role; // 角色   默认是user
            user.setRole("user");
            //private int state; // 是否激活  0 未激活
            user.setState(0);
            //private String activecode; // 激活码  UUID获取
            user.setActivecode(CommonsUtils.getUUID());
            //private Timestamp updatetime; // 更新时间  直接null

            UserService service=new UserService();
            int flag=service.regist(user);//使用标志代表成功和失败 0是成功 1是用户名重复 2是昵称重复 3邮箱重复
            if (flag==0){
                request.getSession().setAttribute("user",user);
                response.sendRedirect(request.getContextPath()+"/jsp/email.jsp");
            }else if (flag==1){
                request.setAttribute("message","用户名已存在");
                request.getRequestDispatcher("/jsp/reg.jsp").forward(request,response);
                return;
            }else if (flag==2){
                request.setAttribute("message","昵称已存在");
                request.getRequestDispatcher("/jsp/reg.jsp").forward(request,response);
                return;
            }else if (flag==3){
                request.setAttribute("message","邮箱已存在");
                request.getRequestDispatcher("/jsp/reg.jsp").forward(request,response);
                return;
            }else {
                response.getWriter().write("服务器维护中");
                response.getWriter().close();
            }


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

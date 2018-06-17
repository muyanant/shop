package shop.service;

import shop.dao.UserDao;
import shop.domain.User;
import utils.MailUtils;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    public int regist(User user) throws SQLException {
        UserDao dao=new UserDao();
        List<User> userList;
        userList=dao.selectUserByusername(user.getUsername());
        if (userList.size()!=0){
            return  1;
        }else {
            userList=dao.selectUserByNickname(user);
            if (userList.size()!=0){
                return  2;
            }else {
                userList=dao.selectUserByEmail(user);
                if (userList.size()!=0){
                    return  3;
                }else {
                    try {
                        dao.addUser(user);
                        //添加用户后发送邮件使用 mailUtil
                        //设置邮箱发送内容 这里本地测试所以用localhost 要是网络上用用域名或者ip
                        String message="注册成功 点击链接激活<a href='http://localhost/active?ActiveCode="+user.getActivecode()+"'>xxxxxxxxxxx<a>";
                        MailUtils.sendMail(user.getEmail(),message);
                        return 0;
                    }catch (SQLException e){
                        throw  new RuntimeException("注册失败");
                    } catch (AddressException e) {
                        e.printStackTrace();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }

                }
            }

        }
        return 4;
    }

    public void findUserByActiveCode(String code) throws SQLException {
        UserDao dao=new UserDao();
        dao.ActiveUserByActiveCode(code);
    }

    /**
     * 后台检查用户名时候重复
     * @param username
     * @return
     */
    public boolean checkUsername(String username) throws SQLException {
        UserDao dao=new UserDao();
        List<User> users = dao.selectUserByusername(username);
        if (users.size()!=0){
            return  true;
        }else {
            return false;
        }
    }

    public User findUser(User user) throws SQLException {
        UserDao dao=new UserDao();
       user = dao.findUserByUsernameAndPassword(user);
      return user;

    }
}

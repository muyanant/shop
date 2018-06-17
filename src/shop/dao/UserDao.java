package shop.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import shop.domain.User;
import utils.DataSourceUtil;
import utils.MD5Utils;

import java.sql.SQLException;
import java.util.List;

public class UserDao {
    /**
     * 通过邮箱找用户
     * @param
     * @return
     * @throws SQLException
     */
    public List<User> selectUserByusername(String username) throws SQLException {
        QueryRunner qr=new QueryRunner(DataSourceUtil.getDataSources());
        String sql="select * from users where username=?";
        return qr.query(sql,new BeanListHandler<User>(User.class),username);
    }

    public List<User> selectUserByNickname(User user) throws SQLException {
        QueryRunner qr=new QueryRunner(DataSourceUtil.getDataSources());
        String sql="select * from users where nickname=?";
        return qr.query(sql,new BeanListHandler<User>(User.class),user.getNickname());

    }


    public List<User> selectUserByEmail(User user) throws SQLException {
        QueryRunner qr=new QueryRunner(DataSourceUtil.getDataSources());
        String sql="select * from users where email=?";
        return qr.query(sql,new BeanListHandler<User>(User.class),user.getEmail());
    }

    /**
     * 添加用户
     * @param user
     * @throws SQLException
     */
    public void addUser(User user) throws SQLException {
        QueryRunner qr=new QueryRunner(DataSourceUtil.getDataSources());
        String sql="insert into users values (null,?,?,?,?,?,?,?,null)";
        //先将用户的密码转化为md5
        String md5Password= MD5Utils.md5(user.getPassword());
        qr.update(sql,user.getUsername(),md5Password,user.getNickname(),user.getEmail(),user.getRole(),user.getState(),user.getActivecode());
    }

    public void ActiveUserByActiveCode(String code) throws SQLException {
        QueryRunner qr=new QueryRunner(DataSourceUtil.getDataSources());
        String sql="update users  set state=? where activecode=?";
        qr.update(sql,1,code);
    }


    public User findUserByUsernameAndPassword(User user) throws SQLException {
        QueryRunner qr=new QueryRunner(DataSourceUtil.getDataSources());
        //这里注意要把user 的密码转为Md5密码
        String password = MD5Utils.md5(user.getPassword());
        String sql="select * from users where username=? and password=?";
        return qr.query(sql,new BeanHandler<User>(User.class),user.getUsername(),password);
    }
}

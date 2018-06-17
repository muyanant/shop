package utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceUtil {
    private  static ComboPooledDataSource cpds=new ComboPooledDataSource();
    private static final ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
    /**
     * 当DBUtils需要手动控制事务时，调用该方法获得一个连接
     *
     * @return
     * @throws SQLException
     *//**
     * 当DBUtils需要手动控制事务时，调用该方法获得一个连接
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return  cpds.getConnection();
    }

    public static ComboPooledDataSource getDataSources(){
        return  cpds;
    }

    // 获取绑定到ThreadLocal中的Connection。
    public static Connection getConnectionByTransaction() throws SQLException {
        Connection con = tl.get();
        if (con == null) {
            con = cpds.getConnection();
            tl.set(con);
        }

        return con;
    }

    // 开启事务
    public static void startTransaction(Connection con) throws SQLException {
        if (con != null)
            con.setAutoCommit(false);
    }

    // 事务回滚
    public static void rollback(Connection con) throws SQLException {
        if (con != null)
            con.rollback();
    }

    public static void closeConnection(Connection con) throws SQLException {
        if (con != null) {
            con.commit();// 事务提交
            con.close();
            tl.remove();

        }
    }

}

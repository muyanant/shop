package utils;

import javax.servlet.http.Cookie;

/**
 * cookie 工具类
 */
public class CookieUtils {
    /**
     * 通过cookie的名字查找cookie
     * @param cookies
     * @param name 要查询的名字
     * @return
     */
    public static Cookie getCookie(Cookie[]cookies,String name){
        if (name==null){
            return  null;
        }
        if (cookies!=null){
            for (Cookie c:cookies){
                if (c.getName().equals(name)){
                    return c;
                }
            }
        }
        return null;
    }
}

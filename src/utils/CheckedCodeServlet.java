package utils;

import com.sun.prism.Graphics;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet(name = "CheckedCodeServlet",urlPatterns = "/check")
public class CheckedCodeServlet extends HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        int width=120;
        int height=40;
        //画验证码
        BufferedImage img=new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB );
        //获得画笔对象
        Graphics2D gc= (Graphics2D) img.getGraphics();

        Color color=gc.getColor();
        gc.setColor(Color.gray);
        gc.fillRect(0,0,width,height);

        gc.setColor(Color.yellow);
        gc.drawRect(0,0,width-1,height-1);

        String words = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        gc.setColor(Color.green);

        gc.setFont(new Font("隶书", Font.BOLD, 30));
        Random random=new Random();

        int x=20;
        int y=20;
        //设置一个校验字符串
       String checkStr = "";

       for (int i=0;i<4;i++){
           int jiaodu = random.nextInt(60)-30;
           double hudu = jiaodu * Math.PI / 180;
           gc.rotate(hudu, x, y);

           int index=random.nextInt(words.length());
           char c=words.charAt(index);
           gc.drawString(c+"",x,y);
           checkStr+=c;
           gc.rotate(-hudu, x, y);
           x+=20;
          
       }
       //画干扰线

        int x1,x2,y1,y2;

       for (int i=0;i<4;i++){
           x1 = random.nextInt(width);
           y1 = random.nextInt(height);
           x2 = random.nextInt(width);
           y2 = random.nextInt(height);
           gc.drawLine(x1, y1, x2, y2);
       }
        //存取校验数组
        request.getSession().setAttribute("checkStr",checkStr.toString());

        // 输出到客户端
        ImageIO.write(img, "jpg", response.getOutputStream());
    }
}

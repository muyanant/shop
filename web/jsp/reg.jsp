<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/8
  Time: 19:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${pageContext.request.contextPath}/js/aj.js"></script>
<!doctype html>
<html>
<head>
    <title>sign in</title>
    <style>
        div{
            background:#009FCC;
            font-size:24px;
            padding:5px;
            color:white;
        }
        form{
            background: #F8F8FF ;
            border:#357FC4 solid 1px;
            color:#575454;
            width:400px;
            margin:20px auto;
            font-size:15px;
        }
        table{
            margin:10px auto;
        }
        a{
            text-decoration:none;
        }
        input[type="button"]{
             background:#228B22;
             color:white;
             font-size:15px;
             font-weight:bold;
             width:120px;
             height:40px;
         }

        input[type="submit"]{
            background:#228B22;
            color:white;
            font-size:15px;
            font-weight:bold;
            width:120px;
            height:40px;
        }

        td:first-child{
            text-align:right;
            padding:0 5px;
        }
        td:only-child{
            text-align:center;
            font-size:12px;
        }
        span:before{
            content:"* ";
            color:red;
        }
        input[type="text"]:read-only{
            border:#888484 solid 2px;
            background:#888484;
            font-weight:bold;
        }
        input[type="text"]:hover{
            background:#EFD9AC;
        }
    </style>

    <script type="text/javascript">
        function show1() {
            var img=document.getElementById("imgId")
            img.src="${pageContext.request.contextPath}/check?"+new Date().getTime()
            return false
        }
        function checkText1() {
            //获得各个属性的元素对象
            var username=document.getElementById("username").value;
            var nickname=document.getElementById("nickname").value;
            var email=document.getElementById("email").value;
            var password=document.getElementById("password").value;
            var repassword=document.getElementById("repassword").value;
            var code=document.getElementById("code").value;
            var checked=document.getElementById("checked").checked;
            //使用邮箱的正则表达式式
            var reg=/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/


            /**/
            var reg1 = /^\s*$/; //代表0个或多个空字符。
            if(reg1.test(username)){
                alert("用户名不能为空");
                return false;
            }


            if (reg1.test(nickname)){
                alert("昵称不能为空");
                return false;
            }

            if(!reg.test(email)){
                alert("邮箱格式不正确");
                return false;
            }
            if (password.length<6){
                alert("密码不能小于6位")
                return false;
            }

            if (password!=repassword){
                alert("两次密码不一致");
                return false;
            }

            if (checked==false){
                alert("请阅读协议");
                return false;
            }
        }

    </script>
</head>
<body>
    <font color="red">${requestScope.message}</font>
<form action="${pageContext.request.contextPath}/reg" method="post" onsubmit="return checkText1()" >
    <div>注册账号</div>
    <table >
        <tr><td><span>用户名</span></td><td><input type="text" name="username" id="username"  /></td></tr>
        <tr><td><span>昵称</span></td><td><input type="text" name="nickname" id="nickname"></td></tr>
        <tr><td><span>email</span></td><td><input type="text" name="email" id="email" /></td></tr>
        <tr><td><span>密码</span></td><td><input type="password" name="password" id="password"/></td></tr>
        <tr><td><span>确认密码</span></td><td><input type="password" name="password" id="repassword"/></td></tr>
        <tr><td><span>输入验证码</span></td><td><input type="text" name="code" id="code"></td><td><a href="#" return onclick="show1()" ><img id="imgId" src="${pageContext.request.contextPath}/check">换张图片</a></td></tr>


        <tr><td colspan="2"><input type="checkbox" id="checked"/>我已看过并接受<a href="/jsp/userneedknow.jsp">《用户协议》</a></td></tr>
        <tr><td colspan="2"><input type="submit" value="立即注册" /></td></tr>
    </table>
</form>
</body>
</html>
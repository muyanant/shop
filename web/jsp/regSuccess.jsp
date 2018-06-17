<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/9
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta http-equiv="refresh" content="3;url=${pageContext.request.contextPath}/default.jsp">
<html>
<head>
    <title>注册成功</title>
    <script type="text/javascript">
        var interval;
        window.onload = function() {
            interval = window.setInterval("fun()", 1000); //设置1秒调用一次fun函数
        };

        function fun() {
            var time = document.getElementById("spanId").innerHTML;

            //判断如果等于0了，不在进行调用fun函数，
            if (time == 0) {
                window.clearInterval(interval);
                return;
            }

            document.getElementById("spanId").innerHTML = ( time- 1);
        }
    </script>
</head>
<body>
    <h1>注册成功，<font color="red"><span id="spanId">3</span></font>秒后自动跳转</h1><br>
    <h1><a href="${pageContext.request.contextPath}/default.jsp" >要是不能跳转请点击</a></h1>

</body>
</html>

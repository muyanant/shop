<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>展示订单</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css" type="text/css" media="screen" />
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/my.js"></script>

</head>

<body>
<table border='1' align="center">
    <tr>
        <td>订单编号</td>
        <td>订单总价</td>

        <td>订单信息</td>
        <td>送货地址</td>
        <td>所属用户</td>
        <td>订单状态</td>
        <td>操作</td>
    </tr>
<c:forEach items="${orders}" var="order" varStatus="vs">

        <tr>
            <td>${order.id}</td>
            <td>${order.money }</td>
           <td><a href="${pageContext.request.contextPath}/findProductByOrder?id=${order.id}"><input type='button' value="查看订单中商品" >
                <div id="div_${order.id}"></div>
           </a>
           </td>
            <td>${order.receiverinfo }</td>
            <td>${order.username} 【${order.nickname}】</td>
            <td>

                <c:if test="${order.paystate==0 }">
                    <a href='${pageContext.request.contextPath}/jsp/pay.jsp?orderid=${order.id}&money=${order.money}'>未支付</a>
                </c:if>

                <c:if test="${order.paystate!=0}">
                    已支付
                </c:if>
            </td>
            <td>
                <c:if test="${order.paystate==0}">
                    <a href="${pageContext.request.contextPath}/delOrder?orderid=${order.id}">删除</a>
                </c:if>

                <c:if test="${order.paystate!=0}">
                    删除
                </c:if>
            </td>
        </tr>

</c:forEach>
</table>


</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/24
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css" type="text/css" media="screen" />
    <!--[if IE 6]><link rel="stylesheet" href="${pageContext.request.contextPath}/style.ie6.css" type="text/css" media="screen" /><![endif]-->
    <!--[if IE 7]><link rel="stylesheet" href="${pageContext.request.contextPath}/    style.ie7.css" type="text/css" media="screen" /><![endif]-->
</head>
<body>
<div class="cleared"></div>
<div class="art-content-layout overview-table">
    <div class="art-content-layout-row">
<c:forEach items="${sessionScope.categoryList}" var="p" varStatus="vs">
    <div class="art-layout-cell">
        <div class="overview-table-inner">
        <h4>${p.name }</h4>
        <img src="${pageContext.request.contextPath}${p.imgurl}" width="100px" height="100px"
             alt="an image" class="image" onclick="findProductById('${p.id}')"/>
        <p>价格: ￥${p.price }</p>
        <p><a href="${pageContext.request.contextPath}/findProductById?id=${p.id}">速速抢购</a></p>
        </div>
    </div>
    <c:if test="${vs.count%5==0}">
<!-- 判断当前已经有5个商品了，这 一行结束，在重新开启一行 -->
    </div> <!-- 判断当前已经有5个商品了，这 一行结束，在重新开启一行 -->
    <div class="art-content-layout-row">

    </c:if>
    </c:forEach>
    <!-- end cell -->
        <h2 > <a href="${pageContext.request.contextPath}/default.jsp">返回首页</a></h2>
</div>

<!-- end row -->
</div>
</body>
</html>

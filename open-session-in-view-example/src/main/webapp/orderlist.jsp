<%@ page language="java" import="java.util.*,de.laliluna.webstock.*;" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>
        My JSP 'order list' starting page
    </title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

</head>

<body>
<c:forEach items="${requestScope.orders}" var="order">
    Ordernumber: ${order.number} <br>
    <c:forEach items="${order.orderLines}" var="orderline">
        ${orderline.quantity}
        ${orderline.article.name}
        <br>
    </c:forEach>
</c:forEach>

</body>
</html>

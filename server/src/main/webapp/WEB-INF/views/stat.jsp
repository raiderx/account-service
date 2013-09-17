<%--
  @author Pavel Karpukhin
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
    <table>
        <tr>
            <td> Per second </td>
            <td> ${perSecond} </td>
        </tr>
        <tr>
            <td> Per minute </td>
            <td> ${perMinute} </td>
        </tr>
    </table>
    <form action="<c:url value="/reset.html"/>" method="post">
        <input type="submit" value="Reset"/>
    </form>
</body>
</html>
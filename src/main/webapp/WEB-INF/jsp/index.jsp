<!DOCTYPE html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Main Page</title>
</head>
<body>
    <div>
        <form:form action="/" method="post" modelAttribute="url">
            <form:label  path="description">Input url to shorten</form:label>
            <form:input  path="value" name="url-to-shorten" type="url"/>
            <input type="submit" value="Shorten">
        </form:form>
    </div>

    <c:choose>
        <c:when test="${pageContext.request.isUserInRole('ADMIN')}">
            <a style="float: right" href = "<c:url value = "/logout"/>">LOG OUT</a>
        </c:when>
        <c:when test="${pageContext.request.isUserInRole('USER')}">
            <a style="float: right" href = "<c:url value = "/logout"/>">LOG OUT</a>
        </c:when>
        <c:otherwise>
            <a style="float: right" href = "<c:url value = "/login"/>">SIGN IN</a>
        </c:otherwise>
    </c:choose>

    <br/>
    <br/>
    <br/>
    <c:if test="${fn:length(urlList) > 0}">
        <table border="2px solid black">
            <tr>
                <th>Full Url</th>
                <th>Short Url</th>
                <th>Short count</th>
                <th>Access count</th>
            </tr>
            <c:forEach items="${urlList}" var="url" varStatus="loop">
                <tr>
                    <td>${url.value}</td>
                    <td><a href="${serverUrl.concat(url.shortenedValue)}">${serverUrl.concat(url.shortenedValue)}</a></td>
                    <td>${url.shortCount}</td>
                    <td>${url.accessCount}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

</body>
</html>
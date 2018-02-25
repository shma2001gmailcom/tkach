<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>inspired by tkach</title>
</head>
<body style="background: #d2d3bf;">
<c:set var="req" value="${pageContext.request}"/>
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}"/>
<c:set var="baseURL" value="${fn:replace(url, fn:substring(uri, 0, fn:length(uri)), req.contextPath)}"/>
<h2>This is ${anArgument}</h2>
<c:forEach var="entry" items="${logs}">
    <c:if test="${entry.key != 'CombinedEventLogger'}">
        <div>
            <c:set var="type" value="${entry.key}"/>
                ${type}
            <div style="color: #808080;display: inline-block;">
                has been used
            </div>
            <a href="${baseURL}/rest/${entry.key}">${entry.value}</a>
            <div style="color: #808080;display: inline-block;">
                times.
            </div>
        </div>
    </c:if>
</c:forEach>
</body>
</html>

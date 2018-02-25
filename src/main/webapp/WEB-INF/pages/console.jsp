<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>console</title>
</head>
<body style="background: #d2d3bf;">
<h2>This is ${anArgument}</h2>
<c:set var="req" value="${pageContext.request}"/>
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}"/>
Console logging details is not available. All information is contained in
<a href="${fn:replace(url, fn:substring(uri, 0, fn:length(uri)), req.contextPath)}/rest/file"> FileEventLogger report.</a>

</body>
</html>


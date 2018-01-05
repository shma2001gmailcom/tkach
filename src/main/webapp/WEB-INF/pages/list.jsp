<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>inspired by tkach</title>
</head>
<body style="background: #d2d3bf;">
   <h2>This is ${anArgument}</h2>
   <c:forEach var="entry" items="${logs}">
       <div>
           ${entry.key}
               <div style="color: #808080;display: inline-block;">
                   has been used
               </div>
                   ${entry.value}
               <div style="color: #808080;display: inline-block;">
                   times.
               </div>
       </div>
   </c:forEach>
</body>
</html>

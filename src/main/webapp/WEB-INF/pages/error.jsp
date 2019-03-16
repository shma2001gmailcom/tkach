<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <script language="javascript">
        function toggle() {
            var element = document.getElementsByClassName('error-details')[0];
            var button = document.getElementsByClassName('expand-collapse')[0];
            var placeholder = document.getElementsByClassName('placeholder')[0];
            if (element.style.display === "block") {
                element.style.display = "none";
                button.innerHTML = "expand";
                placeholder.style = "block";
            } else {
                element.style.display = "block";
                button.innerHTML = "collapse";
                placeholder.style = "none";
            }
        }
    </script>
    <style>
        div.error-details {
            display: none;
            background-color: #e4e5cf;
        }
        div.placeholder {
            background-color: #e4e5cf;
        }
    </style>
    <title>error</title>
</head>
<body style="background: #b8b9a8;">
<h2>This is error details</h2>
Error details are:<br/>
<div class="placeholder">
    <a href="#" class="expand-collapse" onclick="toggle()">...</a>
</div>
<div class="error-details">
    ${details}
</div>
</body>
</html>

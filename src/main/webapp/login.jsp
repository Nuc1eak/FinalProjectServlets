<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:bundle basename="index" prefix="login.">
    <html>
    <head>
        <!-- Bootstrap CSS -->
        <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <!-- bootstrap theme -->
        <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap-theme.css" rel="stylesheet">
        <!--external css-->
        <!-- font icon -->
        <link href="${pageContext.request.contextPath}/resources/bootstrap/css/elegant-icons-style.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/resources/bootstrap/css/font-awesome.css" rel="stylesheet"/>
        <!-- Custom styles -->
        <link href="${pageContext.request.contextPath}/resources/bootstrap/css/style.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resources/bootstrap/css/style-responsive.css" rel="stylesheet"/>
        <title>Login</title>
    </head>
    <style>
        #lang-div img {
            width: 20px;
            height: 20px;
            opacity: 0.7;
        }

        #lang-div img:hover {
            opacity: 1;
        }
    </style>
    <body class="login-img3-body">
    <div class="container">
        <form class="login-form" method="post" action="${pageContext.request.contextPath}/app/login">
            <div class="login-wrap">
                <div style="text-align: right" id="lang-div">
                    <a href="?locale=en"><img src="${pageContext.request.contextPath}/resources/bootstrap/img/icons/us.png"><fmt:message key="lang.en"/></a>
                    <br>
                    <a href="?locale=ua"><img src="${pageContext.request.contextPath}/resources/bootstrap/img/icons/ua.png"><fmt:message key="lang.ua"/></a>
                </div>
                <p class="login-img"><i class="icon_lock_alt"></i></p>
                <div class="form-group">
                    <div class="text-center text-danger"><p><c:out value="${requestScope.info}"/></p></div>
                </div>
                <div class="input-group">
                    <span class="input-group-addon"><i class="icon_profile"></i></span>
                    <input type="text" class="form-control" placeholder="<fmt:message key="placeholder.username"/>" autofocus name="login">
                </div>
                <div class="input-group">
                    <span class="input-group-addon"><i class="icon_key_alt"></i></span>
                    <input type="password" class="form-control" placeholder="<fmt:message key="placeholder.password"/>" name="password">
                </div>
                <button class="btn btn-primary btn-lg btn-block" type="submit"><fmt:message key="button.login"/></button>
                <div class="text-center">
                    <a href="${pageContext.request.contextPath}/app/logout"><fmt:message key="button.deny"/></a>
                </div>
            </div>
        </form>
    </div>
    </body>
    </html>
</fmt:bundle>

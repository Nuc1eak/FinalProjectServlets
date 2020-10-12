<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:bundle basename="index" prefix="storage.">
    <html>
        <head>
            <title><fmt:message key="title"/></title>
        </head>

        <body>
        <section id="container" class="">
            <header class="header dark-bg">
                <div style="text-align: right" id="lang-div">
                    <a href="?locale=en"><img src="${pageContext.request.contextPath}/resources/bootstrap/img/icons/us.png"><fmt:message key="lang.en"/></a>
                    <br>
                    <a href="?locale=ua"><img src="${pageContext.request.contextPath}/resources/bootstrap/img/icons/ua.png"><fmt:message key="lang.ua"/></a>
                </div>
            </header>
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
        </section>
        </body>
    </html>
</fmt:bundle>
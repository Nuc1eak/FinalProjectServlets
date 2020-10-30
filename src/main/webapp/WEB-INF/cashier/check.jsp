<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:bundle basename="cashreg" prefix="cash.">
    <html>
    <head>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
        <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <!-- bootstrap theme -->
        <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap-theme.css" rel="stylesheet">
        <!--external css-->
        <!-- font icon -->
        <link href="${pageContext.request.contextPath}/resources/bootstrap/css/font-awesome.css" rel="stylesheet"/>
        <link href="${pageContext.request.contextPath}/resources/bootstrap/css/elegant-icons-style.css" rel="stylesheet"/>
        <!-- Custom styles -->
        <link href="${pageContext.request.contextPath}/resources/bootstrap/css/style.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resources/bootstrap/css/style-responsive.css" rel="stylesheet"/>

        <!-- javascripts -->
        <script src="${pageContext.request.contextPath}/resources/bootstrap/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
        <!-- nicescroll -->
        <script src="${pageContext.request.contextPath}/resources/bootstrap/js/jquery.scrollTo.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap/js/jquery.nicescroll.js" type="text/javascript"></script>

        <!--custom tagsinput-->
        <script src="${pageContext.request.contextPath}/resources/bootstrap/js/jquery.tagsinput.js"></script>

        <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap-datepicker.js"></script>

        <!-- custom form component script for this page-->
        <script src="${pageContext.request.contextPath}/resources/bootstrap/js/form-component.js"></script>
        <!--custome script for all page-->
        <script src="${pageContext.request.contextPath}/resources/bootstrap/js/scripts.js"></script>
        <title><fmt:message key="title"/></title>
    </head>

    <body>
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
    <section id="container" class="">
        <!--header start-->
        <header class="header dark-bg">
            <div class="top-nav notification-row">
                <ul>
                    <li class="dropdown">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span class="username"><c:out value="${sessionScope.user.firstName}"/> <c:out value="${sessionScope.user.secondName}"/></span>
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu extended">
                            <div class="log-arrow-up"></div>
                            <li>
                                <a href="${pageContext.request.contextPath}/app/logout" style="font-size: 15px;"><i class="icon_key_alt"></i><fmt:message key="logout"/></a>
                            </li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span class="username"><fmt:message key="lang.select"/></span>
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu extended ">
                            <div class="log-arrow-up"></div>
                            <li id="lang-div">
                                <a href="?locale=en" style="font-size: 14px;">
                                    <fmt:message key="lang.en"/><img src="${pageContext.request.contextPath}/resources/bootstrap/img/icons/us.png"/>
                                </a>
                                <a href="?locale=ua" style="font-size: 14px;">
                                    <fmt:message key="lang.ua"/><img src="${pageContext.request.contextPath}/resources/bootstrap/img/icons/ua.png"/>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <!-- user login dropdown end -->
                </ul>
            </div>
        </header>
        <!--header end-->

        <!--main content start-->
        <section id="main-content">
            <section class="wrapper">

                <div class="row">
                    <div class="col-lg-10 align-content-center">
                        <section class="panel">
                            <header class="text-center">
                                <fmt:message key="products"/>
                            </header>

                            <table class="table table-striped table-advance table-hover">
                                <tbody>
                                <tr>
                                    <th><fmt:message key="table.name"/></th>
                                    <th><fmt:message key="table.code"/></th>
                                    <th><fmt:message key="table.quantity"/></th>
                                    <th><fmt:message key="table.measure"/></th>
                                    <th><fmt:message key="table.price"/></th>
                                    <c:if test="${sessionScope.user.role=='superCashier'}">
                                        <th><fmt:message key="table.deleteProductButton"/></th>
                                    </c:if>
                                </tr>
                                <c:forEach var="product" items="${requestScope.productsFromCheck}">
                                    <tr>
                                        <td><c:out value="${product.name}"/></td>
                                        <td><c:out value="${product.code}"/></td>
                                        <td><c:out value="${product.amount}"/></td>
                                        <td><c:out value="${product.measure}"/></td>
                                        <td><c:out value="${product.price}"/></td>
                                        <c:if test="${sessionScope.user.role=='superCashier'}">
                                            <td>
                                                <form id="delete_product_form" method="post" action="${pageContext.request.contextPath}/app/superCashier/deleteProduct">
                                                    <div class="form-group">
                                                        <input type="number" hidden name="check_id" value="${requestScope.checkNumber}"/>
                                                        <input type="number" hidden name="product_delete_id" value="${product.id}"/>
                                                        <button class="btn btn-primary" type="submit"><fmt:message key="table.deleteButton"/></button>
                                                    </div>
                                                </form>
                                            </td>
                                        </c:if>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </section>

                        <nav>
                            <ul class="pagination">
                                <c:forEach var="i" begin="1" end="${requestScope.totalCheckPages}">
                                    <c:choose>
                                        <c:when test="${param.currentCheckPage eq i}">
                                            <li class="page-item active">
                                                <a class="page-link">
                                                        ${i} <span class="sr-only">(current)</span>
                                                </a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item">
                                                <a class="page-link" href="app/expert?page=${i}">
                                                        ${i}
                                                </a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </ul>
                        </nav>
                    </div>
                    <div class="col-lg-1 align-content-center">
                        <h3><fmt:message key="totalPrice"/></h3>
                        <h3><c:out value="${requestScope.totalPrice}"/></h3>
                    </div>
                </div>

                <div class="text-center text-danger"><p><c:out value="${requestScope.exceptionInForm}"/></p></div>
                <form class="form-validate form-horizontal" id="add_product_to_check_form" method="post" action="${pageContext.request.contextPath}/app/cashier/addProductToCheck">
                    <div class="form-row align-items-center">
                        <div class="col-md-3 align-self-center">
                            <label for="product_code" class="control-label col-lg-2"><fmt:message key="table.codeOrName"/></label>
                            <input class="form-control" id="product_code" name="product_code_or_name" type="text"/>
                        </div>
                        <div class="col-md-3 align-self-center">
                            <label for="product_amount" class="control-label col-lg-2"><fmt:message key="table.quantity"/></label>
                            <input class="form-control" id="product_amount" name="product_amount" type="text"/>
                        </div>
                        <div class="col-md-3 align-self-end">
                            <input type="number" hidden name="check_id" value="${requestScope.checkNumber}"/>
                            <button class="btn btn-primary" type="submit"><fmt:message key="button.add"/></button>
                        </div>
                    </div>
                </form>
                <div class="row">
                    <div class="col-sm-2 align-content-center">
                        <c:if test="${sessionScope.user.role=='superCashier'}">
                            <form id="delete_check_form" method="post" action="${pageContext.request.contextPath}/app/superCashier/deleteCheck">
                                <div class="form-group">
                                    <input type="number" hidden name="check_id" value="${requestScope.checkNumber}"/>
                                    <button class="btn btn-danger" type="submit"><fmt:message key="table.deleteButton"/></button>
                                </div>
                            </form>
                        </c:if>
                        <form id="confirm_check_form" method="post" action="${pageContext.request.contextPath}/app/cashier/confirmCheck">
                            <div class="form-group">
                                <input type="number" hidden name="check_id" value="${requestScope.checkNumber}"/>
                                <button class="btn btn-primary" type="submit"><fmt:message key="confirmButton"/></button>
                            </div>
                        </form>
                    </div>
                </div>
                <!-- page end-->
            </section>
        </section>
    </section>
    </body>
    </html>
</fmt:bundle>

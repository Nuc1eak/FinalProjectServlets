<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:bundle basename="expert" prefix="expert.">
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
                        <div class="col-lg-11">
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
                                        <th><fmt:message key="table.changeAmount"/></th>
                                    </tr>
                                    <c:forEach var="product" items="${requestScope.products}">
                                        <tr>
                                            <td><c:out value="${product.name}"/></td>
                                            <td><c:out value="${product.code}"/></td>
                                            <td><c:out value="${product.amount}"/></td>
                                            <td><c:out value="${product.measure}"/></td>
                                            <td><c:out value="${product.price}"/></td>
                                            <td>
                                                <form id="change_amount_form" method="post" action="${pageContext.request.contextPath}/app/expert/changeAmount">
                                                    <div class="form-group">
                                                        <input type="text" hidden name="changed_code" value="${product.code}"/>
                                                        <div class="text-center text-danger"><p><c:out value="${requestScope.wrongChangedAmount}"/></p></div>
                                                        <input type="text" class="form-control" id="change_amount" name="change_amount">
                                                        <button class="btn btn-primary" type="submit"><fmt:message key="table.chButton"/></button>
                                                    </div>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </section>

                            <nav>
                                <ul class="pagination">
                                    <c:forEach var="i" begin="1" end="${requestScope.totalProductPages}">
                                        <c:choose>
                                            <c:when test="${param.currentPage eq i}">
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
                    </div>

                    <form class="form-validate form-horizontal" id="new_product_form" method="post" action="${pageContext.request.contextPath}/app/expert/newProduct">
                        <div class="text-center text-danger"><p><c:out value="${requestScope.productError}"/></p></div>
                        <div class="form-row align-items-center">
                            <div class="col align-self-center">
                                <label for="product_name" class="control-label col-lg-2"><fmt:message key="table.name"/></label>
                                <input class="form-control" id="product_name" name="product_name" type="text"/>
                            </div>
                            <div class="col align-self-center">
                                <label for="product_code" class="control-label col-lg-2"><fmt:message key="table.code"/></label>
                                <input class="form-control" id="product_code" name="product_code" type="text"/>
                            </div>
                            <div class="col align-self-center">
                                <label for="product_amount" class="control-label col-lg-2"><fmt:message key="table.quantity"/></label>
                                <input class="form-control" id="product_amount" name="product_amount" type="text"/>
                            </div>
                            <div class="col align-self-end">
                                <label for="product_measure" class="control-label col-lg-2"><fmt:message key="table.measure"/></label>
                                <select class="form-control m-bot15" id="product_measure" name="product_measure">
                                    <option value="kg"><fmt:message key="value.kg"/></option>
                                    <option value="pc"><fmt:message key="value.pc"/></option>
                                </select>
                            </div>
                            <div class="col align-self-center">
                                <label for="product_price" class="control-label col-lg-2"><fmt:message key="table.price"/></label>
                                <input class="form-control" id="product_price" name="product_price" type="text"/>
                            </div>
                            <div class="col align-self-end">
                                <button class="btn btn-primary" type="submit"><fmt:message key="button.add"/></button>
                            </div>
                        </div>
                    </form>
                    <!-- page end-->
                </section>
            </section>
        </section>
        </body>
    </html>
</fmt:bundle>
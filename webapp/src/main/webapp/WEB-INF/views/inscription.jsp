<%@ page contentType="text/html;charset=UTF-8" %>
 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="lbl.title" text="Computer Database"></spring:message></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="closeSession">
            	<spring:message code="lbl.navbarBrand" text="Application - Computer Database"></spring:message>
            </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="lbl.inscription" text="Inscription"></spring:message></h1>
                    <form action="inscription" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <label for="username">
                                	<spring:message code="lbl.username" text="Username"></spring:message>
                                </label>
                                <spring:message code="lbl.username" var="username" text="Username"/>
                                	<input name="username" type="text" class="form-control" id="username" placeholder="${username}">
                                
                                <label for="password">
                                	<spring:message code="lbl.password" text="Password"></spring:message>
                                </label>
                                <spring:message code="lbl.password" var="password" text="Password"/>
                                	<input name="password" type="text" class="form-control" id="password" placeholder="${password}">
                            </div>
                        </fieldset>
                        <div class="actions pull-right">
                        	<spring:message code="lbl.inscription" var="inscription" text="Inscription"/>
                            	<input type="submit" value="${inscription}" class="btn btn-primary">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>
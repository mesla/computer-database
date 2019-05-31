<%@ page contentType="text/html;charset=UTF-8" %>
 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<title><spring:message code="lbl.title" text="Computer Database"></spring:message></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard?reset=true">
            	<spring:message code="lbl.navbarBrand" text="Application - Computer Database"></spring:message>
            </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${computer.getId()}
                    </div>
                    <h1><spring:message code="lbl.editTitle" text="Edit Computer"></spring:message></h1>

                    <form action="editComputer" method="POST">
                        <input type="hidden" name="computerId" value="${computer.getId()}" id="id"/>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">
                                	<spring:message code="lbl.cptrName" text="Computer name"></spring:message>
                                </label>
                                <input type="text" name="computerName" class="form-control" id="computerName" placeholder="Computer name" value="${computer.getName()}">
                            </div>
                            <div class="form-group">
                                <label for="introduced">
                                	<spring:message code="lbl.cptrIntro" text="Introduced date"></spring:message>
								</label>
                                <input type="date" name="introduced" class="form-control" id="introduced" placeholder="Introduced date" value="${computer.getIntroduced()}" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">
                                	<spring:message code="lbl.cptrDisco" text="Discontinued date"></spring:message>
                                </label>
                                <input type="date" name="discontinued" class="form-control" id="discontinued" placeholder="Discontinued date" value="${computer.getDiscontinued()}" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])">
                            </div>
                            <div class="form-group">
                                <label for="companyId">
									<spring:message code="lbl.companyName" text="Company"></spring:message>
								</label>
                                <select name="companyId" class="form-control" id="companyId">
                                    <option value="0" ${computer.getCompanyId() == null ? "selected" : ''}>None</option>
									<c:forEach var="company" items="${companyList}">
									 	<option value="${company.getId()}" ${computer.getCompanyId() == company.getId() ? "selected" : ''}>${company.getName()}</option>
									</c:forEach>
                                </select>
                            </div>
                        </fieldset>
                        <div class="actions pull-right">
                        	<spring:message code="lbl.editBtn" var="editText" text="Edit"/>
                            	<input type="submit" value="${editText}" class="btn btn-primary">
                            <spring:message code="lbl.or" text="or"></spring:message>
                            <a href="dashboard?page=1" class="btn btn-default">
								<spring:message code="lbl.cancel" text="Cancel"></spring:message>
							</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>
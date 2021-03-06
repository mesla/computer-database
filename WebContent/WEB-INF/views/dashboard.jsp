<%@ page contentType="text/html;charset=UTF-8" %>
 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<title><spring:message code="lbl.title" text="Computer Database"></spring:message></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
<link  href="css/font-awesome.css" rel="stylesheet">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard?reset=true">
				<spring:message code="lbl.navbarBrand" text="Application - Computer Database"></spring:message>
			</a>
			<a href="dashboard?lang=fr" style="float:right; padding:15px;"><img src="pix/logo_fr.png" style="max-width: 20px;"/></a>
			<a href="dashboard?lang=en" style="float:right; padding:15px;"><img src="pix/logo_en.png"style="max-width: 20px;"/></a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${pageInstance.nbComputers}<spring:message code="lbl.homeTitle" text=" Computer(s)."></spring:message></h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="dashboard?page=1" method="GET" class="form-inline">
						<spring:message code="lbl.searchBtn" var="searchText" text="Search name"/>
							<input type="search" id="searchbox" name="search" class="form-control" placeholder="${searchText}" />
						
						<spring:message code="lbl.searchBtn" var="filterText" text="Filter by name"/>
							<input type="submit" id="searchsubmit" value="${filterText}" class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">
						<spring:message code="lbl.addBtn" text="Add Computer"></spring:message>
					</a>
					<a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">
						<spring:message code="lbl.editBtn" text="Edit"></spring:message>
					</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
				<a href="dashboard?orderBy=ORDERBY_COMPUTER_ID_ASC" class="btn btn-default sort-default">
					<spring:message code="lbl.resetOrderByBtn" text="Reset order by"></spring:message>
				</a>
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						
						<th><spring:message code="lbl.cptrName" text="Computer name"></spring:message>
							<a href="dashboard?orderBy=ORDERBY_COMPUTER_NAME_ASC">
								<i class="fas fa-sort-up"></i>
							</a>
							<a href="dashboard?orderBy=ORDERBY_COMPUTER_NAME_DESC">
								<i class="fas fa-sort-down"></i>
							</a>
						</th>
						
						<th><spring:message code="lbl.cptrIntro" text="Introduced date"></spring:message>
							<a href="dashboard?orderBy=ORDERBY_INTRODUCED_ASC">
								<i class="fas fa-sort-up"></i>
							</a>
							<a href="dashboard?orderBy=ORDERBY_INTRODUCED_DESC">
								<i class="fas fa-sort-down"></i>
							</a>
						</th>
							
						<th><spring:message code="lbl.cptrDisco" text="Discontinued date"></spring:message>
							<a href="dashboard?orderBy=ORDERBY_DISCONTINUED_ASC">
								<i class="fas fa-sort-up"></i>
							</a>
							<a href="dashboard?orderBy=ORDERBY_DISCONTINUED_DESC">
								<i class="fas fa-sort-down"></i>
							</a>
						</th>
						
						<th><spring:message code="lbl.companyName" text="Company"></spring:message>
							<a href="dashboard?orderBy=ORDERBY_COMPANY_NAME_ASC">
								<i class="fas fa-sort-up"></i>
							</a>
							<a href="dashboard?orderBy=ORDERBY_COMPANY_NAME_DESC">
								<i class="fas fa-sort-down"></i>
							</a>
						</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach var="computer" items="${computerList}">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.getId()}"></td>
							<td><a href="editComputer?computerId=${computer.getId()}">${computer.getName()}</a>
							</td>
							<td>${computer.getIntroduced() == null ? "-" : computer.getIntroduced()}</td>
							<td>${computer.getDiscontinued() == null ? "-" : computer.getDiscontinued()}</td>
							<td>${computer.getCompanyName() == null ? "-" : computer.getCompanyName()}</td>
						</tr>
					</c:forEach>


				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<li><a href="?page=${(pageInstance.page-1 > 0) ? (pageInstance.page-1) : pageInstance.page}" aria-label="Previous">
					<span aria-hidden="true">&laquo;</span>
				</a></li>
				
				<c:forEach var="page" items="${pageInstance.availablePages}">
					<li><a href="?page=${page}">${page}</a></li>
				</c:forEach>

				<li><a href="?page=${(pageInstance.page < pageInstance.nbPages) ? (pageInstance.page+1) : pageInstance.page}" aria-label="Next">
					<span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>
			<div class="btn-group btn-group-sm pull-right" role="group">
				<a href="?size=10&page=1" class="btn btn-default">10</a>
				<a href="?size=50&page=1" class="btn btn-default">50</a>
				<a href="?size=100&page=1" class="btn btn-default">100</a>
			</div>
		</div>
	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>
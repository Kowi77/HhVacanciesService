<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<portlet:defineObjects />

<head>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.16/datatables.min.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/datatables.min.js"></script>

</head>
<body>

<div class="container-fluid">
    <select name="areas" id="areas"></select>
    <select name="specs" id="specs"></select>
    <button onclick="updateTable($('#areas :selected').val(), $('#specs :selected').val())">Найти вакансии</button>
</div>
<div class="container-fluid">
    <h2>Список вакансий на ${today}</h2>
    <table class="table table-striped display" id="datatable">
        <thead>
        <tr>
            <th>Наименование вакансии</th>
            <th>Дата публикации</th>
            <th>Работодатель</th>
            <th>Зарплата</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>

</body>
</html>

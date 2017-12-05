<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="aui" uri="http://liferay.com/tld/aui" %>--%>
<html>
<portlet:defineObjects />

<head>
   <%-- <script>
        Liferay.Loader.define._amd = Liferay.Loader.define.amd;
        Liferay.Loader.define.amd = false;
    </script>--%>
    <%--<script>$PAGE->requires->js(new moodle_url("webjars/datatables/1.10.15/media/js/jquery.dataTables.min.js"),true);</script>--%>
    <title>Список доступных вакансий на ${today}</title>
    <base href="${pageContext.request.contextPath}/"/>

      <%-- <script src="https://cdn.alloyui.com/3.0.1/aui/aui-min.js"></script>
       <link href="https://cdn.alloyui.com/3.0.1/aui-css/css/bootstrap.min.css" rel="stylesheet"></link>--%>


   <%--<link rel="stylesheet" href="resources/style.css">--%>

    <%--<script type="text/javascript" src="webjars/jquery/3.2.1/dist/jquery.min.js" defer></script>--%>
    <%--<script type="text/javascript" src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js" defer></script>--%>
   <%-- <script type="text/javascript" src="webjars/datatables/1.10.15/media/js/jquery.dataTables.min.js" defer></script>--%>
    <%--<script type="text/javascript" src="webjars/datatables/1.10.15/media/js/dataTables.bootstrap.min.js" defer></script>--%>
   <%-- <script type="text/javascript" src="resources/meetingsUtils.js" defer></script>--%>

  <%--  <link rel="stylesheet" href="webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
    <link rel="stylesheet" href="webjars/datatables/1.10.15/media/css/dataTables.bootstrap.min.css">--%>

    <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>

    <link href="css/datatables.min.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="js/datatables.min.js"></script>

</head>
<body>
This is the <b>HhVacancies</b> portlet with ZLATAN.<br />
<div class="container-fluid">
    <h2>Список вакансий</h2>
    <table class="table table-striped display" id="datatable1">
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

<div class="container-fluid">
    <h2>Список вакансий</h2>
    <div id="vacancyTable"></div>

</div>


<a class="btn btn-primary" href="meeting/0">
    <span class="glyphicon glyphicon-plus" aria-hidden="true">  ДОБАВИТЬ</span>
</a>

</body>
</html>

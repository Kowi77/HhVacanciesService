var ajaxUrl = "services/"; //Базовый URL
var datatableApi;
var areas = [];             //Список регионов
var specializations = []; //Список профессиональных областей
var area = 1202;            //По умолчанию: Новосибирская область
var specialization = 1;   //По умолчанию: Информационные технологии

// Конвертация даты
$.ajaxSetup({
    converters: {
        "text json": function (stringData) {
            var json = JSON.parse(stringData);
            $(json).each(function () {
                this.published_at = this.published_at[0] + "-" + this.published_at[1] + "-" + this.published_at[2] + " " + this.published_at[3] + ":" + this.published_at[4];
            });
            return json;
        }
    }
});

//Новый поиск вакансий
function updateTable(ar, sp) {
    area = ar;
    specialization = sp;
    $.ajax({
        type: "GET",
        url: ajaxUrl + "vacancies/"+ area + "/" + specialization,
        dataType: 'json',
        success: function (data) {
            data.forEach(function (vac) {
                vac.published_at = vac.published_at[0] + "-" + vac.published_at[1] + "-" + vac.published_at[2] + " " + vac.published_at[3] + ":" + vac.published_at[4];
            });
            datatableApi.clear().search("").rows.add(data).draw();
        },
        error: function () {
            errorHandling();
        }
    });
}

function getSpecializations() {
    $.ajax({
        type: "GET",
        url: ajaxUrl + "specializations/",
        dataType: 'json',
        success: function (data) {
            $('#specs :selected').val(specialization)
            specializations = data;
            $("#specs").empty();
            specializations.forEach(function (sp) {
                $("#specs").append("<option value=" + sp.id + ">" + sp.name + "</option>");
            });
            $("option[value=" + specialization + "]").prop("selected", true)
        },
        error: function () {
            errorHandling();
        }
    });
}

function getAreas() {
    $.ajax({
        type: "GET",
        url: ajaxUrl + "areas/",
        dataType: 'json',
        success: function (data) {
            areas = data;
            $('#areas :selected').val(area)
            $("#areas").empty();
            areas.forEach(function (ar) {
                $("#areas").append("<option value=" + ar.id + ">" + ar.name + "</option>");
            });
            $("option[value=" + area + "]").prop("selected", true)
        },
        error: function () {
            errorHandling();
        }
    });
}

//Datatable
jQuery(document).ready(function () {
    $.noConflict();
    datatableApi = jQuery("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl + "vacancies/" + area + "/" + specialization,
            "dataSrc": ""
        },
        "lengthMenu": [ [10, 25, 50, -1], [10, 25, 50, "All"] ],
        "pageLength": 25,
        "bAutoWidth": true,
        "info": false,
        "columns": [
            {"data": "name"},
            {"data": "published_at"},
            {"data": "employer"},
            {"data": "salary"}
        ],
        "order": [[0,"asc"]],
    });
    getAreas();
    getSpecializations();
});

//User's noties creating

function errorHandling() {
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        errorNoty(jqXHR.status, jqXHR.responseText);
    });
}

function successNoty(message) {
    $("#success").css({"display" : ""});
    $("#success").html(message);
    setTimeout(function(){$("#success").css({"display" : "none"})}, 5000);
}

function errorNoty(status, respounce) {
    $("#error").css({"display" : ""});
    $("#error").html("Статус ошибки: " + status + "<br>" + respounce);
    setTimeout(function(){$("#error").css({"display" : "none"})}, 7000);
}
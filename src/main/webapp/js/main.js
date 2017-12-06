/*$('.dropdown')
    .dropdown({
        action: 'hide',
        onChange: function(value, text, $selectedItem) {
            alert(value + "***" + text + "***" + $selectedItem)
        }
    });*/

var ajaxUrl = "services/";
var datatableApi;
var form=$('#detailsForm');

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

//Отрисовка таблицы отфильтрованными данными
function updateTableByData(data) {
    datatableApi.clear().rows.add(data).draw();
}

function updateTable() {
    $.ajax({
        type: "GET",
        url: ajaxUrl + "vacancies/",
        dataType: 'json',
        success: function (data) {
            datatableApi.clear().rows.add(data).draw();
        }
    });
}

//Datatable
jQuery(document).ready(function () {
    $.noConflict();
    datatableApi = jQuery("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl + "vacancies/",
            "dataSrc": ""
        },
        "lengthMenu": [ [10, 25, 50, -1], [10, 25, 50, "All"] ],
        "pageLength": 10,
        "info": false,
        "columns": [
            {"data": "name"},
            {"data": "published_at"},
            {"data": "employer"},
            {"data": "salary"}
        ],
        "order": [[0,"asc"]],
        /*"initComplete": errorHandling*/
    });
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
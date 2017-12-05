/*YUI().use(
    'aui-datatable',
    function(Y) {
        var columns = ['name', 'address', 'city', 'state'];

        var data = [
            {address: '1236 Some Street', city: 'San Francisco', name: 'John A. Smith', state: 'CA'},
            {address: '3271 Another Ave', city: 'New York', name: 'Joan B. Jones', state: 'NY'},
            {address: '9996 Random Road', city: 'Los Angeles', name: 'Bob C. Uncle', state: 'CA'},
            {address: '1623 Some Street', city: 'San Francisco', name: 'John D. Smith', state: 'CA'},
            {address: '9899 Random Road', city: 'Los Angeles', name: 'Bob F. Uncle', state: 'CA'}
        ];

        new Y.DataTable.Base(
            {
                columnset: columns,
                recordset: data
            }
        ).render('#vacancyTable');
    }
);*/
































var ajaxUrl = "services/";
var datatableApi;
var form=$('#detailsForm');

/*$.ajaxSetup({
    converters: {
        "text json": function (stringData) {
            var json = JSON.parse(stringData);
            $(json).each(function () {
                this.date = this.date.replace('T', ' ').substr(0, 16);
            });
            return json;
        }
    }
});*/

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
        "paging": false,
        "info": false,
        "columns": [
            {"data": "name"},
            {"data": "published_at",
                "render": function (data, type, row) {return "<a href=meeting/" + row.id + "/>" + data + ""}},
            {"data": "employer"},
            {"data": "salary"}
        ],
        "order": [[0,"asc"]],
        "initComplete": errorHandling
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
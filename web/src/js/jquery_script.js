'use strict';

$(function() {
    const table = new Table();
    const applicationForm = $("#coordinates-form");
    const clearButton = $("#clear-button");
    clearButton.click(() => table.clear());
    applicationForm.submit({"table": table}, handleFormSubmit);

    table.load();

    $(window).on("beforeunload", function() {
        table.save();
    });

    $.validator.addMethod("openRange", function(value, element, {min=0, max=100} = {}) {
        value = value.replace(",", ".");
        return value > min && value < max;
    }, "The number must be in range");

    applicationForm.validate({
        rules: {
            x: {
                required: true,
            },
            y: {
                required: true,
                openRange: {min: -3, max: 5},
            },
            r: {
                required: true,
            },
        },
        messages: {
            y: {
                openRange: "Число должно быть в диапазоне от -3 до 5 (не включительно)"
            },
        },
        errorPlacement: function(error,element) {
            console.log(element.parents(".value-input"));
            element.parents(".value-input").append(error);
        }
    })
})

class Table {
    #rows;
    constructor() {
        this.table = $("#check-table")
    }
    #createTd(text) {
        return $('<td>')
            .append($('<span>')
                .text(text)
            )
    }
    addRow(data) {
        this.#rows.push(data)
        this.showRow(data)
    }
    showRow(data) {
        this.table.find("tbody")
            .prepend(
                $('<tr>')
                    .append(this.#createTd(data.x))
                    .append(this.#createTd(data.y))
                    .append(this.#createTd(data.r))
                    .append(this.#createTd(data?.hit ? "Попал" : "Не попал"))
                    .append(this.#createTd((new Date(data.created_at)).toLocaleTimeString()))
                    .append(this.#createTd(data.working_time))
            );
    }
    load() {
        this.#rows = JSON.parse(localStorage.getItem("table") || "[]");
        this.table.find("td").remove();
        this.#rows.forEach(row => this.showRow(row));
    }
    save() {
        localStorage.setItem("table", JSON.stringify(this.#rows));
    }
    clear() {
        this.#rows = [];
        this.table.find("td").remove();
    }
}

function serializeFrom(form) {
    const formData = new FormData(form)
    let data = {}
    for (let [name, value] of formData) {
        data[name] = value.replace(",", ".");
    }
    return JSON.stringify(data);
}


function handleFormSubmit(event) {
    event.preventDefault();
    let table = event.data.table
    let requestBody = serializeFrom(event.target);

    function handleResponse(response) {
        table.addRow(response);
    }

    $.ajax({
        url: "/fcgi-bin/lab-1.jar",
        method: "POST",
        contentType: "application/json",
        dataType: "json",
        data: requestBody,
        success: handleResponse,
        error: function (jqxhr, txt_status, err_thr) {
            alert("Error: " + txt_status + ", " + err_thr);
        }
    });
}
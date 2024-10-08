$(document).ready(initTable)

const applicationForm = $("#coordinates-form")
applicationForm.submit(handleFormSubmit)

const clearButton = $("#clear-button")
clearButton.click(clearTable)

const yInput = $("#y-input")
yInput.change(validYConstraint)

function clearTable() {
    let table = {
        "rows": []
    }
    localStorage.setItem("table", JSON.stringify(table))
    $("td").remove()
}

function initTable() {
    if (!localStorage.getItem("table")) {
        let table = {
            "rows": []
        }
        localStorage.setItem("table", JSON.stringify(table))
    }
    else {
        let table = JSON.parse(localStorage.getItem("table"))
        table["rows"].forEach(
            (data) => addNewRowToTable(data)
        )
    }
}

function validYConstraint() {
    let y = yInput.val().replace(",", ".");
    if (y.trim() === "") {
        yInput.get(0).setCustomValidity("Заполните поле");
        return false;
    } else if (!isFinite(y)) {
        yInput.get(0).setCustomValidity("Должно быть число!");
        return false;
    } else if (y >= 5 || y <= -3) {
        yInput.get(0).setCustomValidity("Вы вышли за диапазон (-3; 5)!");
        return false;
    } else {
        yInput.get(0).setCustomValidity("");
        return true;
    }
}

function serializeForm(formNode) {
    const formData = formNode.serializeArray()
    let json = {}
    formData.forEach((pair) => {
        let {name, value} = pair
        json[name] = value
    })
    console.log(json)
    return json
}

function handleFormSubmit(event) {
    event.preventDefault();
    let request = serializeForm(applicationForm)

    $.ajax({
        url: "/fcgi-bin/lab-1.jar",
        method: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(request),
        success: (response) => {
            handleResponse(response)
        },
        statusCode: {
                500: () => console.log("Internal Server Error"),
                400: () => alert("Bad Request")

            }
    });
}


function handleResponse(data) {
    addNewRowToTable(data)
    let table = JSON.parse(localStorage.getItem("table"))
    table["rows"].push(data)
    localStorage.setItem("table", JSON.stringify(table));
}

function addNewRowToTable(data) {
    console.log(data)

    $("#check-table .table-header").after(
        $('<tr>')
            .append($('<td>')
                .append($('<span>')
                    .text(data.x)
                )
            )
            .append($('<td>')
                .append($('<span>')
                    .text(data.y)
                )
            )
            .append($('<td>')
                .append($('<span>')
                    .text(data.r)
                )
            )
            .append($('<td>')
                .append($('<span>')
                    .text(data.hit ? "Попал" : "Не попал")
                )
            )
            .append($('<td>')
                .append($('<span>')
                    .text((new Date(data.created_at)).toLocaleTimeString())
                )
            )
            .append($('<td>')
                .append($('<span>')
                    .text(data.working_time)
                )
            )
    )
}
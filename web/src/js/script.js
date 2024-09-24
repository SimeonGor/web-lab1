const applicantForm = document.getElementById('coordinates-form')
let yField = document.getElementById("y-field")
applicantForm.addEventListener('submit', handleFormSubmit)
yField.addEventListener("input", validYConstraint)
const table = document.getElementById("check")
let point = document.getElementById("point")

$(document).ready(initTable)

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
    let y = yField.value.replace(",", ".");
    if (y.trim() === "") {
        yField.setCustomValidity("Заполните поле");
        return false;
    } else if (!isFinite(y)) {
        yField.setCustomValidity("Должно быть число!");
        return false;
    } else if (y >= 5 || y <= -3) {
        yField.setCustomValidity("Вы вышли за диапазон (-3; 5)!");
        return false;
    } else {
        yField.setCustomValidity("");
        return true;
    }
}

function serializeForm(formNode) {
    const formData = new FormData(formNode)
    let json = {}
    formData.forEach((value, key) => json[key] = value)
    console.log(json)
    return json
}

function showHitCircle(coordinates) {
    let scale = 200 / (2 * coordinates.r)
    point.setAttribute("cx", 150 + (coordinates.x * scale))
    point.setAttribute("cy",150 - (coordinates.y * scale))
    point.setAttribute("visibility", "visible")
}

function handleFormSubmit(event) {
    event.preventDefault();
    let request = serializeForm(applicantForm)
    showHitCircle(request)

    $.ajax({
        url: "/fcgi-bin/lab-1.jar",
        method: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(request),
        success: handleResponse,
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
    let row = table.insertRow(1)
    let x =  row.insertCell(0)
    let y = row.insertCell(1)
    let r = row.insertCell(2)
    let hit = row.insertCell(3)
    let curTime = row.insertCell(4)
    let workTime = row.insertCell(5)

    x.innerHTML = data.x
    y.innerHTML = data.y
    r.innerHTML = data.r
    hit.innerHTML = data.hit ? "Попал" : "Не попал"
    curTime.innerHTML = data.created_at
    workTime.innerHTML = data.working_time
}
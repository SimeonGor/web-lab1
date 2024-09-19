const yField = document.getElementById("y-field");
const xField = document.getElementsByClassName("x-input").item(0);


function checkY() {
    let y = yField.value.replace(",", ".");
    if (y.trim() === ""){
        yField.setCustomValidity("Заполните поле");
        yField.reportValidity();
        return false;
    } else if (!isFinite(y)){
        yField.setCustomValidity("Должно быть число!");
        yField.reportValidity();
        return false;
    } else if (y >= 5 || y <= -3){
        yField.setCustomValidity("Вы вышли за диапазон (-3; 5)!");
        yField.reportValidity();
        return false;
    }
    yField.setCustomValidity("");
    return true;
}

function checkX() {
    xField.querySelector("input[name='x']:checked")
}

function getX() {
    const selectedX = document.querySelector('input[name="x"]:checked');
    if (selectedX) {
        return selectedX.value;
    }
}

function sendRequest() {

}
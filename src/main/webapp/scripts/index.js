window.addEventListener('DOMContentLoaded', getData);

function getData() {
    $.ajax({
        type: "GET",
        url: 'http://localhost:8080/cinema/hall',
        data: parseSearch(getParam())
    }).done(function (data) {
        $('#table').empty().html(data);
    }).fail(function (err) {
        alert(err);
    });
}

const form = document.querySelector("form");

form.addEventListener("submit", (event) => {
    let paramSeat = getParam();
    window.location.href = window.location.origin + '/cinema/payment.html?' + paramSeat;
    event.preventDefault();
}, false);

function getParam() {
    const data = new FormData(form);
    let paramSeat = "";
    for (const entry of data) {
        paramSeat = entry[0] + "=" + entry[1];
    }
    return paramSeat;
}

function parseSearch(search) {
    let place = { row: "", seat: ""};
    if (search.length > 0) {
        const param = search.substring(6, search.length);
        const array = param.split('&');
        const map = new Map();
        array.forEach(item => {
            const index = item.indexOf('_');
            map.set(item.substring(0, index), item.substring(index + 1, item.length));
        });
        place.row = map.get("Row");
        place.seat = map.get("Seat");
    }
    return place;
}

const delay = 15000;
let timer = setTimeout(function request() {
    getData();
    timer = setTimeout(request, delay)
}, delay);

window.addEventListener('DOMContentLoaded', (event) => {
    event.preventDefault();
    const map = parseSearch(window.location.search);
    document.querySelector('#head').innerHTML = `Вы выбрали ряд ${map.get('Row')}, место ${map.get('Seat')}. Сумма 500 рублей.`;
    document.querySelector('form').insertAdjacentHTML('afterbegin', `<input type="hidden" id="seat" name="seat" value=${map.get('Seat')}>`);
    document.querySelector('form').insertAdjacentHTML('afterbegin', `<input type="hidden" id="row" name="row" value=${map.get('Row')}>`);
});

function parseSearch(search) {
    const param = search.substring(7, search.length);
    const array = param.split('&');
    const map = new Map();
    array.forEach(item => {
        const index = item.indexOf('_');
        map.set(item.substring(0, index), item.substring(index + 1, item.length));
    });
    return map;
}
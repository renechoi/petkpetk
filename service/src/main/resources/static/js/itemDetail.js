var price = document.getElementById("price");
var count = document.getElementById("orderCount");
var totalPrice = document.getElementById("totalPrice");
var itemDetailContainer = document.getElementById("itemDetailContainer");
function minusCount() {
    var num = parseInt(count.value);
    var cost = parseInt(price.value);
    if(num > 1) {
        num = num - 1;
        count.value = num;
        totalPrice.innerHTML = cost*num;
    }
}
function plusCount() {
    var num = parseInt(count.value);
    var cost = parseInt(price.value);
    num = num + 1;
    count.value = num;
    totalPrice.innerHTML = cost * num;
}

const DC = document.querySelector(".itemDetailContainer");
window.addEventListener("scroll", () => {
    var width = window.innerWidth;

    if (!itemDetailContainer) {

    } else {

        if (window.scrollY > headerHeight && width < 880) {
            DC.classList.add("jumped3");
            DC.classList.remove("jumped4");
        } else if (window.scrollY > headerHeight && width > 880) {
            DC.classList.remove("jumped3");
            DC.classList.add("jumped4");
        } else {

            DC.classList.remove("jumped4");
            DC.classList.remove("jumped3");
        }
    }
});

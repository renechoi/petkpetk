var totalPrice = parseInt($(".totalPriceTxt").text()) - parseInt($(".deliveryPriceTxt").text()) - parseInt($(".totalDiscountPriceTxt").text());
$(".paymentPriceTxt").text(totalPrice);
function minusItem(itemId) {
    var count = document.getElementById("itemAmount"+itemId);
    var price = document.getElementById("itemPrice"+itemId);

    var num = parseInt(count.value);
    var cost = parseInt(price.textContent);

    if (num > 1) {
        num = num - 1;
        count.value = num;

        $(".totalPriceTxt").text(parseInt($(".totalPriceTxt").text())-cost);

        var totalPrice = parseInt($(".totalPriceTxt").text()) - parseInt($(".deliveryPriceTxt").text()) - parseInt($(".totalDiscountPriceTxt").text());

        $(".paymentPriceTxt").text(totalPrice);
        $("#submitPrice").val((totalPrice+'원 구매하기'));
    }

}

function plusItem(itemId) {
    var count = document.getElementById("itemAmount"+itemId);
    var price = document.getElementById("itemPrice"+itemId);

    var num = parseInt(count.value);
    var cost = parseInt(price.textContent);

    if (num >= 1) {
        num = num + 1;
        count.value = num;

        $(".totalPriceTxt").text(parseInt($(".totalPriceTxt").text())+cost);

        var totalPrice = parseInt($(".totalPriceTxt").text()) - parseInt($(".deliveryPriceTxt").text()) - parseInt($(".totalDiscountPriceTxt").text());

        $(".paymentPriceTxt").text(totalPrice);
        $("#submitPrice").val((totalPrice+'원 구매하기'));
    }

}

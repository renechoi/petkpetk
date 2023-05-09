$(document).ready(function () {

    // $("#paymentItemName").val($("#itemName").val());
    // $("#paymentFinalPaymentPrice").val($("#finalPaymentPrice").val());

    mapPaymentRequest();


});

function mapPaymentRequest(){
    console.log("check")
    $("#paymentItemName").val($("#itemName").text());
    $("#paymentFinalPaymentPrice").val($("#finalPaymentPrice").text());
}
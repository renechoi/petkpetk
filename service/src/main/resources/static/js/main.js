let itemsCount;
let items = 2;
let num = 1;

function showMoreBtn(totalItemCount, num) {
    var divs = document.querySelectorAll('.mainContainer div');
    var moreBtn = document.getElementById("moreBtn");

    if (divs.length >= 12*num && divs.length != $("#itemCount").val()) {

        moreBtn.style.display = "inline";
    }
    if (totalItemCount != null) {
        if (divs.length == totalItemCount) {
            moreBtn.style.display = "none";
        }
    }
}

showMoreBtn(null, 1);




function getItems() {
    itemsCount = items * num;
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: "/api/items",
        type: "post",
        data: {
            page: num
        },
        dataType: "json",
        success: function (datas) {

            var totalItemCount = datas['totalItemCount'];
            var result = datas['result'];

            if (result.length > 0) {
                for (let i = 0; i < result.length; i++) {
                    var content = "";

                    content = "<a href='/item/" + result[i].id + "'><div class='productOneBlock'>" +
                        "<ul>" +
                        "<li class='productPicture'><img class='productImg' src='" + result[i].imageUrl + "' alt='" + result[i].itemName + "'></li>" +
                        "<li class='productName_Status'>";

                    if (result[i].itemStatus === "SOLD_OUT") {
                        content += "<span class='productStatus'>품절</span>";
                        content += "<span class='productName' style='margin-left: 5px;' title='" + result[i].itemName + "'>" + result[i].itemName + "</span></li>";

                    } else {
                        content += "<span class='productName' title='" + result[i].itemName + "'>" + result[i].itemName + "</span></li>";
                    }

                    content += "<li class='productDetail' title='상품 내용'>" + result[i].itemDetail + "</li>" +
                        "<li class='productStar'>";

                    if (result[i].totalRating == 5) {
                        content += "<img class='star' src='/images/star5_0.png'>";

                    } else if (result[i].totalRating == 4.5) {
                        content += "<img class='star' src='/images/star4_5.png'>";

                    } else if (result[i].totalRating == 4) {
                        content += "<img class='star' src='/images/star4_0.png'>";

                    } else if (result[i].totalRating == 3.5) {
                        content += "<img class='star' src='/images/star3_5.png'>";

                    } else if (result[i].totalRating == 3) {
                        content += "<img class='star' src='/images/star3_0.png'>";

                    } else if (result[i].totalRating == 2.5) {
                        content += "<img class='star' src='/images/star2_5.png'>";

                    } else if (result[i].totalRating == 2) {
                        content += "<img class='star' src='/images/star2_0.png'>";

                    } else if (result[i].totalRating == 1.5) {
                        content += "<img class='star' src='/images/star1_5.png'>";

                    } else if (result[i].totalRating == 1) {
                        content += "<img class='star' src='/images/star1_0.png'>";

                    } else if (result[i].totalRating == 0.5) {
                        content += "<img class='star' src='/images/star0_5.png'>";

                    }else if (result[i].totalRating == 0) {
                        content += "<img class='star' src='/images/star5_0.png'>";

                    }

                    content += "<span class='reviewCount' style='margin-left: 5px;'>(" + result[i].reviewCount + ")</span>";
                    content += "</li>";
                    content += "<li class='productPriceDetail'>" +
                        "<span class='originalPrice' style='margin-right: 5px;'>" + result[i].originalPrice + "</span>";

                    if (result[i].discountRate != 0) {
                        content += "<span class='discountRate'>(" + result[i].discountRate + "%)</span>"
                    }
                    content += "<span class='productPrice'>" + result[i].price + "</span><span class='won'>원</span></li>";
                    content += "</ul></div>"

                    $(".mainContainer").append(content);
                }
            }
            showMoreBtn(totalItemCount, num);
            num++;
        }
    })
}




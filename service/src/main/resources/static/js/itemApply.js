var addItemContainer = document.getElementById("addItemContainer");


function getFileName(num) {
    var fileName = $("#itemFile" + num).val();
    var cleanName = fileName.substring(12);
    $(".fileName" + num).val(cleanName);
}

function removeImage(num) {
    $("#itemFile" + num).val(null);
    if (num == 1) {
        $(".fileName" + num).val('대표 이미지')
    } else {
        $(".fileName" + num).val('첨부파일')
    }
}


var addForm = document.getElementById("addForm");
var firstImage = document.getElementById("firstImage");
var addItemName = document.getElementById("addItemName");
var addItemPrice = document.getElementById("itemPrice");
var addAmount = document.getElementById("itemAmount");
var addItemDetail = document.getElementById("itemDetail");

addForm.addEventListener('submit', function (event) {

    if (addItemName == null || addItemName.value == "") {
        alert("상품명을 입력해주세요.");
        event.preventDefault();
        return false;
    }

    if (addItemPrice == null || addItemPrice.value == "") {
        alert("상품 가격을 정해주세요.");
        event.preventDefault();
        return false;
    }

    if (addAmount == null || addAmount.value == "") {
        alert("상품 재고를 입력해주세요.");
        event.preventDefault();
        return false;
    }

    if (addItemDetail == null || addItemDetail.value == "") {
        alert("상품 상세 정보를 입력해주세요.");
        event.preventDefault();
        return false;
    }

    if (firstImage == null || firstImage.value == "대표 이미지") {
        alert("대표 이미지를 정해주세요.");
        event.preventDefault();
        return false;
    }
});


const AC = document.querySelector(".addItemContainer");
window.addEventListener("scroll", () => {
    var width = window.innerWidth;

    if (!addItemContainer) {

    } else {

        if (window.scrollY > headerHeight && width < 880) {
            AC.classList.add("jumped3");
            AC.classList.remove("jumped4");
        } else if (window.scrollY > headerHeight && width > 880) {
            AC.classList.remove("jumped3");
            AC.classList.add("jumped4");
        } else {

            AC.classList.remove("jumped4");
            AC.classList.remove("jumped3");
        }
    }
});
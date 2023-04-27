var price = document.getElementById("price");
var count = document.getElementById("orderCount");
var totalPrice = document.getElementById("totalPrice");
var itemDetailContainer = document.getElementById("itemDetailContainer");
var userEmail = document.getElementById("userEmail");


function minusLike(num, likesNum) {
    clickLikes(num, likesNum, userEmail);
}

function minusCount() {
    var num = parseInt(count.value);
    var cost = parseInt(price.value);
    if (num > 1) {
        num = num - 1;
        count.value = num;
        totalPrice.innerHTML = cost * num;
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

function showDetailBoxs(e) {
    var detailBtn = document.getElementById("detailBtn");
    var review = document.getElementById("reviewBtn");
    var business = document.getElementById("businessBtn");
    var detailBox = document.getElementById("detailBox");
    var reviewBox = document.getElementById("reviewBox");
    var businessBox = document.getElementById("businessBox");

    if (e == detailBtn) {
        detailBox.style.display = "block";
        reviewBox.style.display = "none";
        businessBox.style.display = "none";
    } else if (e == review) {
        detailBox.style.display = "none";
        reviewBox.style.display = "block";
        businessBox.style.display = "none";
    } else if (e == business) {
        detailBox.style.display = "none";
        reviewBox.style.display = "none";
        businessBox.style.display = "block";
    }

}

function likesMouseOn(num) {

    if ($("#likes" + num + "").attr("src") == "/images/likesBefore.png") {
        $("#likes" + num + "").attr("src", "/images/likesHover.png");
    } else {
        $("#likes" + num + "").attr("src", "/images/likesBefore.png");
    }
}

function intoLikeOnclick(num) {
    $("#likes" + num).attr("onclick", "clickLikes(" + num + ", '" + userEmail.value + "')");
}

function clickLikes(num, email) {

    var likeCount = document.getElementById("likeCount" + num);
    if ($("#likes" + num + "").attr("onmouseover") == "intoLikeOnclick(" + num + ")") {

        var intlike = parseInt(likeCount.value);
        var like = document.getElementById("reviewLikes" + num + "")
        $("#likes" + num + "").attr("onmouseover", "likesMouseOn(" + num + "); intoLikeOnclick(" + num + ")");
        $("#likes" + num + "").attr("onmouseout", "likesMouseOn(" + num + ")");
        $("#likes" + num + "").attr("src", "/images/likesHover.png");

        $.ajax({
            url: "/api/review/like",
            type: "post",
            data: {
                num: -1,
                reviewId: num,
                likeNum: intlike,
                userEmail: email
            },
            dataType: "parameters",
            success: function (isPlus) {
                if (isPlus == true) {

                } else {
                    like.innerHTML = "좋아요 취소 실패";
                }
            },
            error: function (XHR, status, error) {
            }
        })
        likeCount.value = intlike - 1;
        like.innerHTML = intlike - 1;

    } else if ($("#likes" + num + "").attr("onmouseover") == "likesMouseOn(" + num + "); intoLikeOnclick(" + num + ")") {
        var like = document.getElementById("reviewLikes" + num + "")

        $("#likes" + num + "").attr("onmouseover", "intoLikeOnclick(" + num + ")");
        $("#likes" + num + "").attr("onmouseout", "");
        $("#likes" + num + "").attr("src", "/images/likesAfter.png");

        var likeCount = document.getElementById("likeCount" + num);
        var intlike = parseInt(likeCount.value);

        $.ajax({
            url: "/api/review/like",
            type: "post",
            data: {
                num: 1,
                reviewId: num,
                likeNum: intlike,
                userEmail: email
            },
            dataType: "parameters",
            success: function (isPlus) {
                if (isPlus == true) {
                } else {
                    like.innerHTML.innerHTML = "좋아요 추가 실패";
                }
            },
            error: function (XHR, status, error) {
            }
        })
        likeCount.value = intlike + 1;
        like.innerHTML = intlike + 1;
    }
}


function showReviewModal(reviewId, itemId) {
    var reviewModalForm = document.getElementById("reviewModalForm");
    var reviewModalContent = document.getElementById("reviewModalContent");
    var reviewContent = document.getElementById("reviewContent" + reviewId + "");
    var cancelModify = document.getElementById("cancelModify");


    cancelModify.setAttribute("onclick", "closeReviewModal(" + reviewId + ")")
    reviewModalContent.value = reviewContent.textContent;
    reviewModalForm.setAttribute("action", "/review/modify/" + itemId + "/" + reviewId);
    reviewModalForm.style.display = "block";

}

function closeReviewModal(reviewId) {
    var reviewModalForm = document.getElementById("reviewModalForm");
    var reviewModalContent = document.getElementById("reviewModalContent");
    var reviewContent = document.getElementById("reviewContent" + reviewId + "");
    console.log(reviewContent.textContent);

    reviewModalContent.value = reviewContent.textContent;
    reviewModalForm.style.display = "none";
}

function getFileName(num) {
    var fileName = $("#reviewFile" + num).val();
    var cleanName = fileName.substring(12);
    $(".fileName" + num).val(cleanName);
}

function removeImage(num) {
    $("#reviewFile" + num).val(null);
    $(".fileName" + num).val('첨부파일')
}

var imageIdx = 0;
function getPath(e, reviewId) {
    var images = document.getElementsByClassName("reviewImages"+reviewId);
    var path = $(e).attr('src');
    popImage(path, reviewId);
    imageIdx = Array.prototype.indexOf.call(images, e);
    if (images.length == 1) {
        $(".previousImageBtn").css("display", "none");
        $(".nextImageBtn").css("display", "none");
    }else {
        if (imageIdx == (images.length - 1)) {
            $(".nextImageBtn").css("display", "none");
        } else if (imageIdx == 0) {
            $(".previousImageBtn").css("display", "none");
        }
    }

}

function popImage(path, reviewId) {
    $(".image-wrap" + reviewId).css("display", "flex").show();
    $(".images" + reviewId)
        .html("<div class='btnPlace'><input type='button' value='◀' class='previousImageBtn' onclick='preImage("+reviewId+")'></div><div class='imageZomBox'><input class='closeBtn' type='button' value='✖' onclick='closeImage("+reviewId+")'><img src='" + path + "' class='zomInImage'></div><div class='btnPlace'><input type='button' value='▶' class='nextImageBtn' onclick='nextImage("+reviewId+")'></div>")
        .css({width: '100%', height: '100%'});

}

function preImage(reviewId) {
    var images = document.getElementsByClassName("reviewImages"+reviewId);
    imageIdx--;

    var path = images[imageIdx].getAttribute("src");
    console.log(path);
    $(".zomInImage").attr("src", path);

    if (imageIdx == 0) {
        $(".previousImageBtn").css("display", "none");
        $(".nextImageBtn").css("display", "block");
    } else {
        $(".previousImageBtn").css("display", "block");
        $(".nextImageBtn").css("display", "block");
    }
}
function nextImage(reviewId) {
    var images = document.getElementsByClassName("reviewImages"+reviewId);
    imageIdx++;

    var path = images[imageIdx].getAttribute("src");
    console.log(path);
    console.log(imageIdx);
    console.log(images.length);
    $(".zomInImage").attr("src", path);

    if(imageIdx == (images.length-1)) {
        $(".nextImageBtn").css("display", "none");
        $(".previousImageBtn").css("display", "block");
    }else {
        $(".previousImageBtn").css("display", "block");
        $(".nextImageBtn").css("display", "block");
    }

    }

function closeImage(reviewId) {
    $(".images" + reviewId).animate({width: '0%', height: '0%'});
    $(".image-wrap" + reviewId).hide();
    $(".previousImageBtn").css("display", "");
    $(".nextImageBtn").css("display", "");
    imageIdx = 0;
}
var addItemContainer = document.getElementById("addItemContainer");
var itemMngContainer = document.getElementById("itemMngContainer");
var itemDetailContainer = document.getElementById("itemDetailContainer");
var NoticeSearchZone = document.getElementById("NoticeSearchZone");

var width = window.innerWidth;

if (width > 880) {
    if (addItemContainer || itemMngContainer || itemDetailContainer) {
        NoticeSearchZone.style.display = "none";
    } else {
        NoticeSearchZone.style.display = "flex";
    }
}else{
    if (addItemContainer || itemMngContainer || itemDetailContainer) {
        NoticeSearchZone.style.display = "none";
    } else {
        NoticeSearchZone.style.display = "block";
    }
}



window.addEventListener("resize", () => {
    var width = window.innerWidth;
    if (width > 880) {
        if (addItemContainer || itemMngContainer || itemDetailContainer) {
            NoticeSearchZone.style.display = "none";
        } else {
            NoticeSearchZone.style.display = "flex";
        }
    } else {
        if (addItemContainer || itemMngContainer || itemDetailContainer) {
            NoticeSearchZone.style.display = "none";
        } else {
            NoticeSearchZone.style.display = "block";
        }
    }

});




var addItemContainer = document.getElementById("addItemContainer");
var itemMngContainer = document.getElementById("itemMngContainer");
var NoticeSearchZone = document.getElementById("NoticeSearchZone");


window.addEventListener("resize", () => {
    var width = window.innerWidth;
    if (width > 880) {
        if (addItemContainer || itemMngContainer) {
            NoticeSearchZone.style.display = "none";
        } else {
            NoticeSearchZone.style.display = "flex";
        }
    } else {
        if (addItemContainer || itemMngContainer) {
            NoticeSearchZone.style.display = "none";
        } else {
            NoticeSearchZone.style.display = "block";
        }
    }

});

function re(){
    var width = window.innerWidth;
    if (width > 880) {
        if (addItemContainer || itemMngContainer) {
            NoticeSearchZone.style.display = "none";
        } else {
            NoticeSearchZone.style.display = "flex";
        }
    } else {
        if (addItemContainer || itemMngContainer) {
            NoticeSearchZone.style.display = "none";
        } else {
            NoticeSearchZone.style.display = "block";
        }
    }
}

re();


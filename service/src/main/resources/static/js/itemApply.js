var addItemContainer = document.getElementById("addItemContainer");


$("#file").on('change', function () {
    var fileName = $("#file").val();
    $(".upload-name").val(fileName);
});

function getFileName(num) {
    var fileName = $("#itemFile" + num).val();
    var cleanName = fileName.substring(12);
    $(".fileName" + num).val(cleanName);
}


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
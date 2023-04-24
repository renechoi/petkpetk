var divs = document.querySelectorAll('.mainContainer div');

function showMoreBtn(){
    console.log(divs.length);

    if(divs.length >= 12) {
        var moreBtn = document.getElementById("moreBtn");
        moreBtn.style.display = "inline";
    }else{

    }
}
showMoreBtn();




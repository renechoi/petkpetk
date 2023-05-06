function sortArticles() {
    const sortValue = $('input[name="sort"]:checked').val();
    $.ajax({
        type: 'GET', url: generateSortUrl(sortValue), dataType: 'html', success: function (data) {
            const articles = $(data).find('tbody').html();
            const pagination = $(data).find('.pagination').html();
            $('tbody').html(articles);
            $('.pagination').html(pagination);
        }
    });
}


$("#category-apply").on("click", function () {
    const categories = $('input[type="checkbox"][name="category"]:checked')
        .map(function () {
            return this.value;
        }).get();

    const searchValue = categories.join(",");
    const searchType = document.getElementById("category-search-type");

    $.ajax({
        type: "GET", url: generateSearchUrl(searchType.value, searchValue), dataType: "html", success: function (data) {
            const articles = $(data).find('tbody').html();
            const pagination = $(data).find('.pagination').html();
            $('tbody').html(articles);
            $('.pagination').html(pagination);
        }
    });
});



function generateSortUrl(sortValue) {
    const baseUrl = window.location.href.split('?')[0];
    const queryParam = `sort=${sortValue}`;

    return window.location.href.includes("?") ? `${baseUrl}?${queryParam}` : `${window.location.href}?${queryParam}`;
}


function generateSearchUrl(searchType, searchValue) {
    const baseUrl = window.location.href.split('?')[0];
    const queryParam = `searchType=${searchType}` + `&searchValue=${searchValue}`;

    return window.location.href.includes("?") ? `${baseUrl}?${queryParam}` : `${window.location.href}?${queryParam}`;
}

var num = 1;
function getMoreArticles() {
    $.ajax({
        url : "/api/articles",
        type: "post",
        data : {
            page : num,
            searchType : $("#searchType").val(),
            searchValue : $("#searchValue").val()
        },
        dataType : "json",
        success: function (result) {
            console.log("가져옴");
            var article = result['article'];
            var totalCount = result['totalCount'];

            for (let i = 0; i < article.length; i++) {
                console.log(article[i]);

                if (i % 3 == 0) {

                    var content = "";
                    content += "<div><div class='oneArticleBox'><a href='/community/articles/"+article[i].id+"'>";

                    if (article[i].rawImages != null) {
                        content+="<img class='articleFirstImage' src='"+article[i].rawImages[0]+"'>";
                    }

                    content += "<div class='articleContentBox'>"+
                                        "<div class='articleTitle'>"+article[i].title+"</div>"+
                                        "<div class='articleContent'>" +
                                            "<div class='contentDetail'>"+article[i].content+"</div>"+
                                        "</div>";

                    if (article[i].hashtags != null) {
                        content += "<div class='hashTagBox'>";
                        for (let j = 0; j < article[i].hashtags.length; j++) {
                            content += "<span class='hashTag'>"+article[i].hashtags[j]+"</span>";
                        }
                        content += "</div>";
                    }

                    content += "</div></a></div></div>";



                    $("#articleLine1").append(content);
                }else if (i % 3 == 1) {
                    var content = "";
                    content += "<div><div class='oneArticleBox'><a href='/community/articles/"+article[i].id+"'>";

                    if (article[i].rawImages != null) {
                        content+="<img class='articleFirstImage' src='"+article[i].rawImages[0]+"'>";
                    }

                    content += "<div class='articleContentBox'>"+
                        "<div class='articleTitle'>"+article[i].title+"</div>"+
                        "<div class='articleContent'>" +
                        "<div class='contentDetail'>"+article[i].content+"</div>"+
                        "</div>";

                    if (article[i].hashtags != null) {
                        content += "<div class='hashTagBox'>";
                        for (let j = 0; j < article[i].hashtags.length; j++) {
                            content += "<span class='hashTag'>"+article[i].hashtags[j]+"</span>";
                        }
                        content += "</div>";
                    }

                    content += "</div></a></div></div>";

                    $("#articleLine2").append(content);

                } else if(i % 3 == 2) {
                    var content = "";
                    content += "<div><div class='oneArticleBox'><a href='/community/articles/"+article[i].id+"'>";

                    if (article[i].rawImages != null) {
                        content+="<img class='articleFirstImage' src='"+article[i].rawImages[0]+"'>";
                    }

                    content += "<div class='articleContentBox'>"+
                        "<div class='articleTitle'>"+article[i].title+"</div>"+
                        "<div class='articleContent'>" +
                        "<div class='contentDetail'>"+article[i].content+"</div>"+
                        "</div>";

                    if (article[i].hashtags != null) {
                        content += "<div class='hashTagBox'>";
                        for (let j = 0; j < article[i].hashtags.length; j++) {
                            content += "<span class='hashTag'>"+article[i].hashtags[j]+"</span>";
                        }
                        content += "</div>";
                    }

                    content += "</div></a></div></div>";

                    $("#articleLine3").append(content);
                }
            }
            showMoreBtn(totalCount);
        }
    });
    num++;
}

function showPost() {
    var postCat = document.getElementById("postCat");

    if (postCat.style.display == "none") {
        postCat.style.display = "block";
    } else {
        postCat.style.display = "none";
    }
}

function showMoreBtn(totalCount) {
    var oneArticleBoxs = document.getElementsByClassName("oneArticleBox");
    var getMoreBtn = document.getElementById("getMoreBtn");

    if (oneArticleBoxs.length < totalCount) {
        getMoreBtn.style.display = "block";
    } else {
        getMoreBtn.style.display = "none";
    }
}

showMoreBtn($("#totalCount").val());

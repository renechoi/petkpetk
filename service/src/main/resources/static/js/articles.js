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



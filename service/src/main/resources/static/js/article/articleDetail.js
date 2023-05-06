var commentForm = document.getElementById("commentForm");

if (commentForm) {
    commentForm.addEventListener("submit", function (event) {
        event.preventDefault();
        $("#articleId").val($(".articleId").val());
        $("#userEmail").val($(".userEmail").val());

        if ($(".commentContents").val() == "") {
            alert("댓글 내용을 입력해주세요.");
            $(".commentContents").focus();
            return false;
        }

        commentForm.submit();
    });
}


function showModifyCommentBox(commentId) {
    var newCommentForm = document.getElementById("newCommentForm" + commentId);
    var comment = document.getElementById("comment"+commentId);
    var newComment = document.getElementById("newComment"+commentId);
    var commentBtns = document.getElementById("commentBtns" + commentId);
    var cancelOrCompleteBtns = document.getElementById("cancelOrCompleteBtns" + commentId);

    newComment.value = comment.textContent;

    newComment.focus();

    newCommentForm.style.display = "block";
    cancelOrCompleteBtns.style.display = "flex";
    comment.style.display = "none";
    commentBtns.style.display = "none";
}

function cancelModifyComment(commentId) {
    var newCommentForm = document.getElementById("newCommentForm" + commentId);
    var comment = document.getElementById("comment"+commentId);
    var newComment = document.getElementById("newComment"+commentId);
    var commentBtns = document.getElementById("commentBtns" + commentId);
    var cancelOrCompleteBtns = document.getElementById("cancelOrCompleteBtns" + commentId);

    newComment.value = comment.textContent;

    newCommentForm.style.display = "none";
    cancelOrCompleteBtns.style.display = "none";
    comment.style.display = "block";
    commentBtns.style.display = "flex";
}

function submitModifyComment(commentId) {
    var newCommentForm = document.getElementById("newCommentForm" + commentId);
    var newComment = document.getElementById("newComment"+commentId);

    if (newComment.value == "") {
        alert("수정 할 댓글 내용을 적어주세요.")
        return false;
    }
    newCommentForm.submit();
}
// import FetchWrapper from "./FetchWrapper.js";

class CommentManager {
    constructor() {
        this.commentDeleteBtns = document.getElementsByClassName("comment-delete-btn");
        for (let commentDeleteBtn of this.commentDeleteBtns) {
            commentDeleteBtn.addEventListener("click", this.deleteComment);
        }
    }

    // deleteComment = async (e) => {
    //     let btn = e.target;
    //     let commentId = btn.getAttribute("data-id");
    //     const response = FetchWrapper.callApi('POST', "/comments/" + commentId + "/delete")
    //     console.log(response);
    //     // window.location.reload();
    // }
}

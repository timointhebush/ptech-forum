class Comment {
    constructor(postId) {
        this.postId = postId;
        this.csrfMetaTag = document.getElementsByName('_csrf')[0];
        this.firstLoad = true;
        this.totalComments = 0;
        this.size = 0;

        this.commentsDiv = document.getElementById("comments");
        this.commentsPaginationUl = document.getElementById("comments-pagination");

        this.commentSubmitBtn = document.getElementById("comment-submit-btn");
        if (this.commentSubmitBtn) {
            this.commentSubmitBtn.addEventListener("click", this.submitComment);
        }
        this.initComments();
    }

    initComments = async () => {
        await this.showCommentsOfPage(1);
        this.firstLoad = false;
    }

    showCommentsOfPage = async (page) => {
        let commentsPage = await this.fetchCommentsPage(page);
        this.totalComments = commentsPage["totalElements"];
        this.size = commentsPage["size"];
        await this.updatePageItems(commentsPage);
        await this.updateComments(commentsPage);
        if (!this.firstLoad) {
            this.commentsDiv.scrollIntoView();
        }
    }

    updateComments = async (commentsPage) => {
        let commentsTemplateData = await fetch("/js/templates/comments.hbs").then((res) => res.text());
        let commentsTemplate = Handlebars.compile(commentsTemplateData);
        this.commentsDiv.innerHTML = commentsTemplate({
            "comment": commentsPage['content']
        });
    }

    updatePageItems = async (commentsPage) => {
        this.commentsPaginationUl.innerHTML = '';
        let commentPageLiTemplateUrl = "/js/templates/comment-page-item.hbs";
        let commentPageLiTemplateData = await fetch(commentPageLiTemplateUrl).then((res) => res.text());
        this.appendPageItems(commentsPage, commentPageLiTemplateData);
        let commentPageItems = document.getElementsByClassName("comment-page-item");
        for (let commentPageItem of commentPageItems) {
            let page = parseInt(commentsPage['number']) + 1;
            if (commentPageItem.getAttribute("data-page") === String(page)) {
                commentPageItem.classList.add("active");
            }
            commentPageItem.addEventListener("click", (e) => {
                let nxtPage = e.target.parentNode.getAttribute("data-page");
                this.showCommentsOfPage(nxtPage);
            });
        }
    }

    appendPageItems = (commentsPage, commentPageLiTemplateData) => {
        let currentPage = commentsPage['number'] + 1
        let totalPages = commentsPage['totalPages'];
        let numOfIndexes = 0;
        let startPage = Math.max(1, currentPage - 2);
        let additionalLeft =  2 - (totalPages - currentPage);
        if (additionalLeft > 0) {
            startPage -= additionalLeft;
            startPage = Math.max(1, startPage);
        }
        if (currentPage > 1) {
            this.commentsPaginationUl.appendChild(this.createLiNode(commentPageLiTemplateData, {
                "data-page": currentPage - 1,
                "idx": '이전'
            }));
        }
        while (numOfIndexes < 5 && startPage <= totalPages) {
            this.commentsPaginationUl.appendChild(this.createLiNode(commentPageLiTemplateData, {
                "data-page": startPage,
                "idx": startPage
            }));
            numOfIndexes += 1;
            startPage += 1;
        }
        if (currentPage < totalPages) {
            this.commentsPaginationUl.appendChild(this.createLiNode(commentPageLiTemplateData, {
                "data-page": currentPage + 1,
                "idx": '다음'
            }));
        }
    }

    createCommentsPageUrl = (page) => {
        return "/posts/" + this.postId + "/comments?page=" + page;
    }

    fetchCommentsPage = async (page) => {
        let url = this.createCommentsPageUrl(page);
        return await fetch(url).then((response) => response.json());
    }

    createLiNode = (templateData, valuesObj) => {
        let commentPageLiTemplate = Handlebars.compile(templateData);
        let liText = commentPageLiTemplate(valuesObj);
        let div = document.createElement('div');
        div.innerHTML = liText.trim();
        return div.firstChild;
    }

    submitComment = async (event) => {
        event.preventDefault();
        try {
            let commentForm = document.getElementById("comment-form");
            let commentFormData = new FormData(commentForm);
            const response = await fetch("/comments", {
                method: "POST",
                body: commentFormData
            });
            if (response.ok) {
                let lastPage = parseInt((this.totalComments + 1) / this.size);
                if ((this.totalComments + 1) % this.size !== 0) {
                    lastPage += 1;
                }
                document.getElementById("comment-input").value = "";
                await this.showCommentsOfPage(lastPage);
            } else {
                throw new Error("댓글 작성 중 오류가 발생했습니다.");
            }
        } catch (error) {
            alert(error.message);
        }
    }
}
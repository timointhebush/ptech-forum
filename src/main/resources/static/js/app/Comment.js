class Comment {
    constructor(postId) {
        this.postId = postId;
        this.csrfMetaTag = document.getElementsByName('_csrf')[0];
        this.firstLoad = true;

        this.commentsDiv = document.getElementById("comments");
        this.commentsPaginationUl = document.getElementById("comments-pagination");
        this.initComments();
    }

    initComments = async () => {
        await this.showCommentsOfPage(1);
        this.firstLoad = false;
    }

    showCommentsOfPage = async (page) => {
        let commentsPage = await this.fetchCommentsPage(page);
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
        if (commentsPage['number'] > 0) {
            this.commentsPaginationUl.appendChild(this.createLiNode(commentPageLiTemplateData, {
                "data-page": commentsPage['number'],
                "idx": '이전'
            }));
        }
        if (commentsPage['totalPages'] < 5) {
            for (let i=0; i < commentsPage['totalPages']; i++) {
                this.commentsPaginationUl.appendChild(this.createLiNode(commentPageLiTemplateData, {
                    "data-page": i + 1,
                    "idx": i + 1
                }));
            }
        } else {
            for (let i=commentsPage['number']-2; i<commentsPage['number']+2; i++) {
                this.commentsPaginationUl.appendChild(this.createLiNode(commentPageLiTemplateData, {
                    "data-page": i + 1,
                    "idx": i + 1
                }));
            }
        }
        if (commentsPage['number'] < commentsPage['totalPages'] - 1) {
            this.commentsPaginationUl.appendChild(this.createLiNode(commentPageLiTemplateData, {
                "data-page": commentsPage['number'] + 2,
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
}
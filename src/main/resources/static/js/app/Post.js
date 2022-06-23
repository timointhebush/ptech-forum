class Post {
    constructor(postId) {
        this.deleteFileIds = [];
        this.deleteFileIdsInput = document.getElementById("delete-file-ids-input");
        this.fileDeleteBtns = document.getElementsByClassName("file-delete-btn");
        this.fileInput = document.getElementById("file-input");
        this.fileNum = 0;
        this.postEditBtn = document.getElementById("post-edit-btn");
        this.postDeleteBtn = document.getElementById("post-delete-btn");
        this.csrfMetaTag = document.getElementsByName('_csrf')[0];
        this.postId = postId;
        if (this.fileDeleteBtns) {
            this.fileNum = this.fileDeleteBtns.length;
            for (let i = 0; i < this.fileNum; i++) {
                this.fileDeleteBtns[i].addEventListener("click", this.removeAttachment)
            }
        }
        if (this.fileInput) {
            this.fileInput.addEventListener("click", (e) => {
                if (this.fileNum !== 0) {
                    e.preventDefault();
                    alert("첨부 파일은 최대 1개 업로드할 수 있습니다.");
                }
            })
        }
        if (this.postEditBtn) {
            this.postEditBtn.addEventListener("click", (e) => {
                e.preventDefault();
                if (!confirm("게시글을 수정하시겠습니까?")) {
                    return
                }
                let postForm = document.getElementById("post-form");
                let postFormData = new FormData(postForm);
                let postId = postFormData.get('id');
                try {
                    const response = fetch("/posts/" + postId, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json',
                            'X-CSRF-TOKEN': postFormData.get('_csrf')
                        },
                        body: JSON.stringify(postFormData)
                    });
                    window.location.href = '/posts/' + postId;
                } catch(error) {
                    alert("게시글 수정 중 오류가 발생했습니다.");
                }
            })
        }
        if (this.postDeleteBtn) {
            this.postDeleteBtn.addEventListener("click", this.deletePost);
        }
    };

    removeAttachment = (e) => {
        let attachmentFile = e.target.parentNode;
        this.deleteFileIds.push(Number(attachmentFile.getAttribute("data-id")));
        attachmentFile.remove();
        this.deleteFileIdsInput.value = this.deleteFileIds;
        this.fileNum--;
    }

    deletePost = async () => {
        try {
            const response = await fetch("/posts/" + this.postId, {
                method: "DELETE",
                headers: {
                    'X-CSRF-TOKEN': this.csrfMetaTag.getAttribute("content")
                }
            });
            location.href = "/posts";
        } catch (error) {
            alert("게시글 삭제 중 오류가 발생했습니다.");
        }
    }
}
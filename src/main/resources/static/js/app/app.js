let deleteBtns = document.getElementsByClassName("btn-delete");
if (deleteBtns) {
    for (let deleteBtn of deleteBtns) {
        deleteBtn.addEventListener("click", (e) => {
            if (!confirm("삭제하시겠습니까?")) {
                e.preventDefault();
                return 0;
            }
        })
    }
}
class Table {
    constructor() {
        this.clickableRows = document.getElementsByClassName('clickable-row');
        if (this.clickableRows) {
            for (let clickableRow of this.clickableRows) {
                clickableRow.addEventListener("click", this.clickRow);
            }
        }
    }

    clickRow = (e) => {
        let row = e.target.parentNode;
        location.href = row.getAttribute('data-url');
    }
}
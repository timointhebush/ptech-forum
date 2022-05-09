class InputFileManager {
    constructor() {
        this.$inputFile = $('.input-file')
        this.initEvent();
    }

    initEvent = () => {
        var self = this;
        self.$inputFile.on("change", function (){
            let fileName = $(this).val().split('\\').pop();
            $(this).next('.custom-file-label').addClass("selected").html(fileName);
            self.readURL();
        });
    }

    readURL = () => {
        const self = this;
        const input = self.$inputFile;

        if (input[0].files.length > 0) {
            let reader = new FileReader();
            reader.onload = function (e) {
                if (input[0].files.length > 1) {
                    $('.custom-file-label').text(input[0].files.length + '개 파일');
                } else {
                    $('.custom-file-label').text(input[0].files[0].name);
                }
            }

            reader.readAsDataURL(input[0].files[0]);
        }
    }
}
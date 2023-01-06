class FileUploader {
    static #instance = null;

    fileList = new Array();
    fileInput = null;
    fileUploadForm = null;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new FileUploader();
        }

        return this.#instance;
    }

    constructor() {
        this.setFileUploadEvent();
        this.setDragDropEvent();
        this.setCancelButtonEvent();
    }

    setFileUploadEvent() {
        $(".file-input").change(() => {
            const formData = new FormData($("form")[0]);
            let changeFlag = false;

            formData.forEach(value => {
                if(value.size != 0) {
                    this.fileList.push(value);
                    changeFlag = true;
                }
            })

            if(changeFlag) {
                $(".file-input").empty();
                this.setImagePreview();
            }

        })
    }

    setImagePreview() {
        this.makeMainUploadContent();
        this.changeHeader();

        this.fileList.forEach((file, index) => {
            const reader = new FileReader();

            reader.onload = (e) => {
                $(".swiper-wrapper").append(`
                    <div class="swiper-slide">
                        <img src='${e.target.result}' alt="">
                    </div>
                `)
            }

            setTimeout(() => {
                reader.readAsDataURL(file), index * 100
            })
        })

        this.makeSwiperButton();
        this.makeSwiperFooter();
        this.makeSwiper1();
        this.makeUploadedFileListDiv();
    }

    makeMainUploadContent() {
        $(".upload-file-div").html(`
            <div class="swiper-container swiper1">
                <div class="swiper-wrapper">

                </div>
            </div>
        `);
    }

    changeHeader() {
        $(".main-modal-div .pre-button, .main-modal-div .next-button").removeClass("hidden");
    }

    makeSwiperButton() {
        $(".swiper1").append(`
            <div class="swiper-button-prev">&lt;</div>
            <div class="swiper-button-next">&gt;</div>
        `);

        this.setPreButtonEvent();
        this.setNextButtonEvent();
    }

    makeSwiperFooter() {
        $(".swiper1").append(`
            <div class="swiper-footer">
                <div class="left-icons">
                    <button class="modify-file-ratio-button" type="button">
                        <i class="fa-solid fa-up-right-and-down-left-from-center"></i>
                    </button>
                    <button class="expansion-button" type="button">
                        <i class="fa-solid fa-magnifying-glass-plus"></i>
                    </button>
                </div>
                <div class="swiper-pagination"></div>
                <div class="right-icons">
                    <button class="show-file-list-button" type="button">
                        <i class="fa-regular fa-clone"></i>
                    </button>
                </div>
            </div>
        `);

        this.setShowFileListButtonEvent();
    }

    setShowFileListButtonEvent() {
        $(".show-file-list-button").click(() => {
            const uploadFileListDiv = $(".uploaded-file-list-div");

            if(uploadFileListDiv.hasClass("visible")) {
                uploadFileListDiv.removeClass("visible");
            }else {
                uploadFileListDiv.addClass("visible");
            }
        })
    }

    makeUploadedFileListDiv() {
        $(".swiper1").append(`
            <div class="uploaded-file-list-div visible">
                <div class="file-list-div">
                </div>
            </div>
        `);

        this.fileList.forEach((file, index) => {
            const reader = new FileReader();

            reader.onload = (e) => {
                $(".file-list-div").append(`
                    <div class="uploaded-file-div">
                        <div class="cancel-mark-div">
                            <i class="fa-solid fa-x"></i>
                        </div>
                        <img src="${e.target.result}" alt="">
                    </div>
                `);

                if(index + 1 == this.fileList.length) {
                    $(".file-list-div").append(`
                        <form class="add-file-input-form" enctype="multipart/form-data">
                            <input id="add-file-input" class="add-file-input visible" type="file" name="file" multiple>
                            <label class="add-file-label" for="add-file-input">+</label>
                        </form>
                    `);

                    this.setFileRemoveButtonEvent();
                    this.setAddFileInputEvent();
                }
            }
            
            setTimeout(() => {
                reader.readAsDataURL(file), index * 100
            })
        });

    }

    setFileRemoveButtonEvent() {
        let fileList = this.fileList;

        const setImagePreview = () => {
            this.fileList = fileList;
            this.setImagePreview();
        }

        const initializationUploadContent = () => {
            this.initializationUploadContent();
        }

        $(".cancel-mark-div").click(function() {
            const removeIndex = $(".cancel-mark-div").index(this);

            fileList = fileList.filter((file, index) => index != removeIndex);
            
            fileList.length == 0 ? initializationUploadContent() : setImagePreview();
        })
 
    }

    setAddFileInputEvent() {
        $(".add-file-input").change(() => {
            let changeFlag = false;
            const formData = new FormData($("form")[0]);

            formData.forEach(value => {
                if(value.size != 0) {
                    changeFlag = true;
                    this.fileList.push(value);
                }
            })

            if(changeFlag) {
                $(".add-file-input").empty();
                this.setImagePreview();
            }
        })
    }

    setPreButtonEvent() {
        $(".main-modal-div .pre-button").click(() => {
            this.initializationUploadContent();
        })
    }

    initializationUploadContent() {
        $(".main-modal-div .pre-button, .main-modal-div .next-button").addClass("hidden");
        $(".upload-file-div").html(`
            <form class="file-upload-form" enctype="multipart/form-data">
                <img src="/static/image/images/upload-picture-and-video.png" alt="업로드 이미지">
                <p class="notice-p">사진과 동영상을 여기에 끌어다 놓으세요.</p>
                <input id="file-input" class="file-input visible" name="file" type="file" multiple>
                <label class="select-file-label" for="file-input">컴퓨터에서 선택</label>
            </form>
        `)

        $(".upload-picture-and-video-modal").removeClass("write-board-modal-view");
        $(".main-modal-div .next-button").text("다음");
        $(".option-content-div, .content-div").addClass("visible");
        $(".right-icons, .left-icons").removeClass("hidden");

        this.fileList.length = 0;
        this.setFileUploadEvent();
        this.setDragDropEvent();
        // Tag.getInstance().visibleTagDiv();

        $(".on-off-input").attr("checked", false);
        $(".on-off-outer-label").removeClass("on off");
        $(".on-off-div").removeClass("move-left move-right");
    }

    setNextButtonEvent() {
        $(".main-modal-div .next-button").click(() => {
            $(".upload-picture-and-video-modal").addClass("write-board-modal-view");
            $(".content-div").removeClass("visible");
            $(".right-icons, .left-icons").addClass("hidden");

            $(".main-modal-div .next-button").text("공유하기");
            this.setContentOptionClickEvent();
            // Tag.getInstance().setClickEventForTag();
        })
    }

    setContentOptionClickEvent() {
        // $(".main-modal-div .next-button").click(() => {

        // })

        $(".accessibility-div .option-title-div").click(() => {
            if($(".accessibility-div .option-content-div").hasClass("visible")) {
                $(".accessibility-div .option-content-div").removeClass("visible");
                $(".accessibility-div .option-icon-div").html(`<i class="fa-solid fa-chevron-up"></i>`);
            
            }else {
                $(".accessibility-div .option-content-div").addClass("visible");
                $(".accessibility-div .option-icon-div").html(`<i class="fa-solid fa-chevron-down"></i>`);
            }
        })

        $(".advanced-settings .option-title-div").click(() => {
            if($(".advanced-settings  .option-content-div").hasClass("visible")) {
                $(".advanced-settings .option-content-div").removeClass("visible");
                $(".advanced-settings .option-icon-div").html(`<i class="fa-solid fa-chevron-up"></i>`);

            }else {
                $(".advanced-settings .option-content-div").addClass("visible");
                $(".advanced-settings .option-icon-div").html(`<i class="fa-solid fa-chevron-down"></i>`);

            }
        })

        $(".on-off-input").change(function() {
            if($(this).is(":checked")) {
                if($(this).hasClass("hide-like-and-view-input")) {
                    $(".hide-like-and-view-option-div label").addClass("on");
                    $(".hide-like-and-view-option-div .on-off-div").addClass("move-right");

                }else {
                    $(".comment-option-div label").addClass("on");
                    $(".comment-option-div .on-off-div").addClass("move-right");

                }
            }else {
                if($(this).hasClass("hide-like-and-view-input")) {
                    $(".hide-like-and-view-option-div label").removeClass("on");
                    $(".hide-like-and-view-option-div .on-off-div").removeClass("move-right");

                }else {
                    $(".comment-option-div label").removeClass("on");
                    $(".comment-option-div .on-off-div").removeClass("move-right");

                }
            }
        })
        
    }

    makeSwiper1() {
        const swiper1 = new Swiper(".swiper1", {
            slidesPerView: 1,
            spaceBetween: 400,
            allowTouchMove: false,
            navigation: {
                nextEl: '.swiper1 .swiper-button-next',
                prevEl: '.swiper1 .swiper-button-prev'
            },
            pagination: {
                el: '.swiper-pagination'
            }
        })
    }

    setDragDropEvent() {
        $(".file-upload-form").on({
            'dragover': (e) => {
                e.stopPropagation();
                e.preventDefault();
            },
            'drop': (e) => {
                e.preventDefault();
                this.uploadFile(e);
            }}
        )
    }

    uploadFile(e) {
        let fileList = e.originalEvent.dataTransfer.files;
        let changeFlag = false;

        for(let i = 0; i < fileList.length; i++) {
            if(fileList[i].size != 0) {
                this.fileList.push(fileList[i]);
                changeFlag = true;
            }
        }

        if(changeFlag) {
            this.setImagePreview();
        }

    }

    setCancelButtonEvent() {
        $(".main-modal-div .x-mark-div").click((e) => {
            if(this.fileList.length > 0) {
                $(".remove-notice-modal-outer-div").removeClass("visible");
                $(".remove-notice-modal-outer-div .remove-button").click(() => {
                    this.modalDivVisible();
                    this.initializationUploadContent();
                })
                $(".remove-notice-modal-outer-div .cancel-button").click(() => {
                    $(".remove-notice-modal-outer-div").addClass("visible");
                })
                return false;
            }
            this.modalDivVisible();
        })
        
    }

    modalDivVisible() {
        $(".main-modal-div").addClass("visible");
        $(".remove-notice-modal-outer-div").addClass("visible");
    }
}

class BoardContent {
    static #instance = null;

    contentTextArea = null;

    userTagFlag = false;
    placeTagFlag = false;

    userTagList = new Array();
    placeTagList = new Array();

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new BoardContent();
        }

        return this.#instance;
    }

    constructor() {
        this.contentTextArea = document.querySelector(".content-textarea");
        this.setCharLength();
    }

    setContentKeyPressEvent() {
        this.contentTextArea.onkeydown = (e) => {

            setTimeout(() => {
                this.setCharLength();

                const keyCode = e.keyCode;
                let content = null;
                // @ => 50
                // # => 51
                // spacebar => 32

                if(this.userTagFlag) {
                    content = this.contentTextArea.value;

                    alert("content: " + content);
                    
                    this.userTagList.forEach(userTag => {
                        const regExp = new RegExp(userTag, "g");
                        content = content.replace(regExp, "");
                        alert("replace: " + content);
                        
                    })

                    const tagIndex = content.indexOf("@") + 1;
                    const spaceIndex = content.indexOf(" ", tagIndex);
                    // alert("tagIndex: " + tagIndex);
                    // alert("spaceIndex: " + spaceIndex);
                    content = content.substring(tagIndex, spaceIndex == -1 ? content.length : spaceIndex);

                    // alert("substring: " + content);

                    if(content.length > 0) {
                        alert("db에서 값을 가져옵니다. " + content);
                    }



                }else if(this.placeTagFlag) {
                    content = this.contentTextArea.value;

                    alert("content: " + content);
                    
                    this.placeTagList.forEach(placeTag => {
                        const regExp = new RegExp(placeTag, "g");
                        content = content.replace(regExp, "");
                        // alert("replace: " + content);
                        
                    })

                    const tagIndex = content.indexOf("#") + 1;
                    const spaceIndex = content.indexOf(" ", tagIndex);
                    // alert("tagIndex: " + tagIndex);
                    // alert("spaceIndex: " + spaceIndex);
                    content = content.substring(tagIndex, spaceIndex == -1 ? content.length : spaceIndex);

                    // alert("substring: " + content);

                    if(content.length > 0) {
                        alert("db에서 값을 가져옵니다. " + content);
                    }

                }
    
                if(keyCode == 50) {
                    this.userTagFlag = true;
    
                } else if(keyCode == 51) {
                    this.placeTagFlag = true;

                } else if(keyCode == 32 && (this.userTagFlag || this.placeTagFlag)) {
                    if(content != null) {
                        this.userTagFlag ? this.userTagList.push("@" + content.trim())
                        : this.placeTagList.push("#" + content.trim());

                    }
                    this.userTagFlag = false;
                    this.placeTagFlag = false;

                }
            }, 100);
            
        }
    }

    setCharLength() {
        const maxLength = 2200;

        const text = this.contentTextArea.value;
        const textSize = text.length;
        
        let totalLength = textSize;

        if(totalLength > maxLength) {
            this.contentTextArea.value = text.substring(0, maxLength - 1);
            
        }
    
        document.querySelector(".count-span").textContent = totalLength + "/" + 2200;
    
    }
}


class Tag {
    static #instance = null;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new Tag();
        }

        return this.#instance;
    }

    setClickEventForTag() {
        $(".swiper1").click(function(e) {
            let divTop = e.clientY;
            let divLeft = e.clientX -40;

            $(".tag-div").removeClass("visible");

            $(".tag-div").css({
                "top": divTop,
                "left": divLeft
            })
        })
    }

    visibleTagDiv() {
        $(".tag-div").addClass("visible");
    }
}
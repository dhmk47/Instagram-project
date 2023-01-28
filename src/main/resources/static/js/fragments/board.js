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
                const fileType = FileUploader.getInstance().getFileType(value);
                console.log(fileType);
                if(value.size != 0) {
                    if(FileUploader.getInstance().checkAllowedFileType(changeFlag, fileType)) {
                        this.fileList.push(value);
                        changeFlag = true;
                        
                    }
                }
            })

            if(changeFlag) {
                $(".file-input").empty();
                this.setImagePreview();
            }

        })
    }

    getFileType(file) {
        return file.type.substring(0, file.type.indexOf("/"));
    }

    checkAllowedFileType(changeFlag, fileType) {
        const allowFileTypeList = ["image", "video"];

        if(allowFileTypeList.indexOf(fileType) == -1) {
            alert("허용되지 않는 확장자입니다.\n업로드 가능 확장자: 이미지, 동영상");
            changeFlag = false;
            return false;
        }

        return true;
    }

    setImagePreview() {
        this.makeMainUploadContent();
        this.changeHeader();

        this.fileList.forEach((file, index) => {
            const reader = new FileReader();

            reader.onload = (e) => {
                $(".swiper-wrapper").append(`
                    ${file.type.indexOf("image") != -1 ? `
                    <div class="swiper-slide">
                        <img src='${e.target.result}' alt="">
                    </div>`
                    : `
                    <div class="swiper-slide">
                        <video autoplay>
                            <source src="${e.target.result}" type="video/mp4">
                        </video>
                    </div>
                    `}
                    
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
                        ${file.type.indexOf("image") != -1 ?
                        `
                            <img src="${e.target.result}" alt="">
                        `
                        :
                        `
                            <div class="swiper-slide">
                                <video>
                                    <source src="${e.target.result}" type="video/mp4">
                                </video>
                            </div>
                        `}
                    </div>
                `);

                if(index + 1 == this.fileList.length) {
                    $(".file-list-div").append(`
                        <form class="add-file-input-form" enctype="multipart/form-data">
                            <input id="add-file-input" class="add-file-input visible" type="file" name="file" accept="image/*, video/*" multiple>
                            <label class="add-file-label" for="add-file-input">+</label>
                        </form>
                    `);

                    this.setFileRemoveButtonEvent();
                    this.setAddFileInputEvent();
                }
            }
            
            setTimeout(() => {
                reader.readAsDataURL(file), index * 500
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
                const fileType = FileUploader.getInstance().getFileType(value);
                console.log(fileType);
                if(value.size != 0) {
                    if(FileUploader.getInstance().checkAllowedFileType(changeFlag, fileType)) {
                        changeFlag = true;
                        this.fileList.push(value);

                    }
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
            BoardContent.getInstance().setCreateBoardButtonClickEvent();
            // Tag.getInstance().setClickEventForTag();
        })
    }

    setContentOptionClickEvent() {
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
                const fileType = FileUploader.getInstance().getFileType(fileList[i]);
                console.log(fileType);

                if(this.checkAllowedFileType(changeFlag, fileType)) {
                    this.fileList.push(fileList[i]);
                    changeFlag = true;

                }
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
    locationTagFlag = false;

    userTagList = new Array();
    locationTagList = new Array();

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

                    // alert("content: " + content);
                    
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
                        const userList = Tag.getInstance().getUserForUserTag(content);
                        this.setUserSearchList(userList);
                    }



                }else if(this.locationTagFlag) {
                    content = this.contentTextArea.value;

                    // alert("content: " + content);
                    
                    this.locationTagList.forEach(placeTag => {
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
                        const locationList = Tag.getInstance().getLocationForLocationTag(content);
                        this.setLocationSearchList(locationList);
                    }

                }
    
                if(keyCode == 50) {
                    this.userTagFlag = true;
    
                } else if(keyCode == 51) {
                    this.locationTagFlag = true;

                } else if(keyCode == 32 && (this.userTagFlag || this.locationTagFlag)) {
                    if(content != null) {
                        this.userTagFlag ? this.userTagList.push("@" + content.trim())
                        : this.locationTagList.push("#" + content.trim());

                    }
                    this.userTagFlag = false;
                    this.locationTagFlag = false;

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

    setCreateBoardButtonClickEvent() {
        $(".main-modal-div .next-button").click((e) => {
            this.createBoardRequest();
            e.stopPropagation();
        })
    }

    createBoardRequest() {
        const createBoardFormData = this.getCreateBoardFormData();

        $.ajax({
            async: false,
            type: "post",
            url: "/api/v1/board",
            enctype: "multipart/form-data",
            contentType: false,
            processData: false,
            data: createBoardFormData,
            dataType: "json",
            success: (response) => {
                if(response.data) {
                    alert("게시글 작성 성공");
                    location.replace("/");
                }else {
                    alert("게시글 작성 실패");
                }
            },
            error: (request, status, error) => {
                console.log(request.status);
                console.log(request.responseText);
                console.log(error);
            }
        })
    }

    getCreateBoardFormData() {
        let createBoardFormData = new FormData(document.querySelector("form"));
        createBoardFormData.append("content", $(".content-textarea").val());
        createBoardFormData.append("hideViewAndLikeCountFlag", $(".hide-like-and-view-input").is(":checked"));
        createBoardFormData.append("disableCommentFlag", $(".comment-option-input").is(":checked"));
        createBoardFormData.append("userCode", Principal.getInstance().user.userCode);
        createBoardFormData.append("boardTypeCode", 1);
        createBoardFormData.append("userTagList", this.userTagList);
        createBoardFormData.append("locationTagList", this.locationTagList);

        FileUploader.getInstance().fileList.forEach(file => {
            createBoardFormData.append("boardFileList", file);
        })

        return createBoardFormData;
    }

    setUserSearchList(userList) {
        $(".tag-search-result-madal-div ul").empty();
        this.showTagSearchResultModalDiv();

        console.log(userList);

        userList.forEach(user => {
            const userProfile = user.userDetail.userProfileImage == null ? "github-logo.png" : user.userDetail.userProfileImage;

            $(".tag-search-result-madal-div ul").append(`
                <li>
                    <div class="detail-information-div">
                        <img class="user-image" src="/image/profiles/${userProfile}" alt="userProfile">
                        <div class="user-information">
                            <span class="user-nickname-span">${user.userNickname}</span>
                            <span class="user-name-span">${user.userName}</span>
                        </div>
                    </div>
                </li>
            `);
        })

    }

    setLocationSearchList(locationList) {
        $(".tag-search-result-madal-div ul").empty();
        this.showTagSearchResultModalDiv();

        console.log(locationList);

        locationList.forEach(location => {
            $(".tag-search-result-madal-div ul").append(`
                <li>
                    <div class="location-tag-result-list-div">
                        <span class="tag-content-span">${location.tagName}</span>
                        <span class="tag-content-total-count-span">${location.totalCount}</span>
                    </div>
                </li>
            `);
        })
    }

    showTagSearchResultModalDiv() {
        $(".tag-search-result-modal-div").removeClass("visible");
    }

    hideTagSearchResultModalDiv() {
        $(".tag-search-result-modal-div").addClass("visible");
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

    getUserForUserTag(value) {
        let userList = null;

        $.ajax({
            async: false,
            type: "get",
            url: `/api/v1/user/list?searchValue=${value}`,
            dataType: "json",
            success: (response) => {
                userList = response.data;
            },
            error: (request, status, error) => {
                console.log(request.status);
                console.log(reqeust.responseText);
                console.log(error);
            }
        })

        return userList;
    }

    getLocationForLocationTag(value) {
        let locationList = null;

        $.ajax({
            async: false,
            type: "get",
            url: `/api/v1/location-tag/information/list?searchValue=${value}`,
            dataType: "json",
            success: (response) => {
                locationList = response.data;
            },
            error: (request, status, error) => {
                console.log(request.status);
                console.log(reqeust.responseText);
                console.log(error);
            }
        })

        return locationList;
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
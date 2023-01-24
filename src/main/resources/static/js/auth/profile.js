window.onload = () => {
    EventSetter.getInstance();
    Search.getInstance();
    FileUploader.getInstance();
    BoardContent.getInstance().setContentKeyPressEvent();
    ProfilePageLoader.getInstance();
    ProfilePageEventSetter.getInstance();
}

class ProfilePageLoader {
    static #instance = null;

    userProfileData = null;
    boardPageFlag = false;
    savedBoardPageFlag = false;
    taggedBoardPageFlag = false;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new ProfilePageLoader();
        }

        return this.#instance;
    }

    constructor() {
        this.loadProfileByUserNickname();
        this.setBoardHeaderClass();
    }

    loadProfileByUserNickname() {
        const userNickname = this.getUserNicknameByUri();

        $.ajax({
            async: false,
            type: "get",
            url: `/api/v1/user/profile/detail?userNickname=${userNickname}`,
            dataType: "json",
            success: (response) => {
                this.setUserProfile(response.data);
            },
            error: (request, status, error) => {
                console.log(request.status);
                console.log(request.responesText)
                console.log(error);
            }
        })
    }

    getUserNicknameByUri() {
        const pathName = location.pathname;
        const lastSourceFromUri = pathName.substring(pathName.lastIndexOf("/") + 1);
        let userNickname = null;

        if(lastSourceFromUri == "saved" || lastSourceFromUri == "tagged") {
            userNickname = pathName.substring(pathName.indexOf("/") + 1, pathName.lastIndexOf("/"));
            lastSourceFromUri == "saved" ? this.savedBoardPageFlag = true : this.taggedBoardPageFlag = true;

        }else {
            userNickname = lastSourceFromUri;
            this.boardPageFlag = true;

        }

        return userNickname;
    }

    setUserProfile(userData) {
        const user = Principal.getInstance().user;
        this.userProfileData = userData;

        console.log(this.userProfileData);
        document.title = `${this.userProfileData.userName}(@${this.userProfileData.userNickname}) • Instagram`;
        const userProfileImgae = this.userProfileData.userProfileImage == null ? 'github-logo.png' : this.userProfileData.userProfileImage;
        
        $(".user-information-main-div").html(`
            <div class="user-image-div">
                <img src="/image/profiles/${userProfileImgae}" alt="userProfileImage">
            </div>
            <div class="user-information-div">
                <div class="nickname-and-option-div">
                    <div>
                        <span class="nickname-span">${this.userProfileData.userNickname}</span>
                    </div>
                    <div>
                        <button class="profile-edit-button" type="button">프로필 편집</button>
                    </div>
                    <div>
                        <i class="fa-solid fa-gear setting-button"></i>
                    </div>
                </div>
                <div class="board-and-follow-div">
                    <div class="board-information-div">
                        <span>게시물</span>
                        <span class="board-span">${this.userProfileData.boardList.length}</span>
                    </div>
                    <div class="follower-information-div">
                        <span>팔로워</span>
                        <span class="follower-span">${this.userProfileData.followerCount}</span>
                    </div>
                    <div class="following-information-div">
                        <span>팔로우</span>
                        <span class="following-span">${this.userProfileData.followingCount}</span>
                    </div>
                </div>
                <div class="user-name-div">
                    <span class="user-name-span">${this.userProfileData.userName}</span>
                </div>
                <div class="introduce-div">
                    <span class="introduce-span">${this.userProfileData.introduceContent}</span>
                </div>
            </div>
        `);

        $(".board-header-div").html(`
            <div class="board-button-div">
                <span class="content-span">게시물</span>
            </div>
            ${user.userCode != this.userProfileData.userCode ? '' : `
            <div class="saved-board-button-div">
                <span class="content-span">저장됨</span>
            </div>`}
            <div class="tag-button-div">
                <span class="content-span">태그됨</span>
            </div>
        `);
    }

    setBoardHeaderClass() {
        if(this.boardPageFlag) {
            $(".board-button-div").addClass("select-board-header");
            $(".board-button-div span").addClass("select-board-header-span");
        }else if(this.savedBoardPageFlag) {
            $(".saved-board-button-div").addClass("select-board-header");
            $(".saved-board-button-div span").addClass("select-board-header-span");
            
        }else {
            $(".tag-button-div").addClass("select-board-header");
            $(".tag-button-div span").addClass("select-board-header-span");

        }
    }

    setShowUploadBoardList() {
        const uploadBoardList = this.userProfileData.boardList;
        $(".upload-board-list-div").removeClass("visible");

        uploadBoardList.forEach((uploadBoard, index) => {

        })
    }

    setShowSavedBoardList() {
        const savedBoardList = this.userProfileData.savedBoardList;
        $(".saved-board-list-div").removeClass("visible");

        savedBoardList.forEach((savedBoard, index) => {

        })
    }

    setShowTaggedBoardList() {
        const taggedBoardList = this.userProfileData.taggedBoardList;
        $(".tag-board-list-div").removeClass("visible");

        taggedBoardList.forEach((taggedBoard, index) => {

        })
    }
}

class ProfilePageEventSetter {
    static #instance = null;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new ProfilePageEventSetter();
        }

        return this.#instance;
    }

    constructor() {
        this.setBoardHeaderDivClickEvent();
    }

    setBoardHeaderDivClickEvent() {
        const userNickname = ProfilePageLoader.getInstance().userProfileData.userNickname;
        
        $(".board-button-div").click(() => {
            location.href = `/${userNickname}`;
        })

        $(".saved-board-button-div").click(() => {
            location.href = `/${userNickname}/saved`;
        })

        $(".tag-button-div").click(() => {
            location.href = `/${userNickname}/tagged`;
        })
    }
}
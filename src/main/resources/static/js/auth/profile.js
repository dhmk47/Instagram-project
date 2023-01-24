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
        
        $(".user-image-div").html(`
            <img src="/image/profiles/${userProfileImgae}" alt="userProfileImage">
        `);

        $(".nickname-span").text(this.userProfileData.userNickname);
        $(".user-name-span").text(this.userProfileData.userName);
        $(".introduce-span").text(this.userProfileData.introduceContent);

        $(".board-span").text(this.userProfileData.boardList.length);
        $(".follower-span").text(this.userProfileData.followerCount);
        $(".following-span").text(this.userProfileData.followingCount);

        if(user.userCode != this.userProfileData.userCode) {
            $(".saved-board-button-div").addClass("visible");
        }
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
            location.href = `/%{userNickname}/tagged`;
        })
    }
}
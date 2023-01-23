window.onload = () => {
    EventSetter.getInstance();
    Search.getInstance();
    FileUploader.getInstance();
    BoardContent.getInstance().setContentKeyPressEvent();
    Profile.getInstance();
    ProfilePageLoader.getInstance();
}

class ProfilePageLoader {
    static #instance = null;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new ProfilePageLoader();
        }

        return this.#instance;
    }

    constructor() {

    }

    loadProfileByUserNickname() {
        const userNickname = this.getUserNicknameByUri();

        $.ajax({
            async: false,
            type: "get",
            url: `/api/v1/user/${userNickname}`,
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
        return location.pathname.substring(location.pathname.lastIndexOf("/") + 1);
    }

    setUserProfile(userData) {
        console.log(userData);
        document.title = `${userData.userName}(@${userData.userNickname}) • Instagram`;
        const userProfileImgae = userData.userDetail.userProfileImage == null ? 'github-logo.png' : userData.userDetail.userProfileImage;
        
        $(".user-image-div").html(`
            <img src="/image/profiles/${userProfileImgae}" alt="userProfileImage">
        `);

        $(".nickname-span").text(userData.userNickname);
        $(".user-name-span").text(userData.userName);
        $(".introduce-span").text(userData.userDetail.introduce);
    }
}
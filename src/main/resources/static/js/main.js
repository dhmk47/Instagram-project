window.onload = () => {
    EventSetter.getInstance();
    FileUploader.getInstance();
    BoardContent.getInstance().setContentKeyPressEvent();
    Profile.getInstance();
}

class Profile {
    static #instance = null;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new Profile();
        }

        return this.#instance;
    }

    constructor() {
        this.setUserProfileInformation();
    }

    setUserProfileInformation() {
        const user = Principal.getInstance().user;
        const userProfileImgae = user.userDetail.profileImage;
        $(".detail-information-div").empty().append(`
            <img class="user-image" src="/image/profiles/${userProfileImgae == null ? 'github-logo.png' : userProfileImgae}" alt="profileImage">
            <div class="user-information">
                <span class="user-nickname-span">${user.userNickname}</span>
                <span class="user-name-span">${user.userName}</span>
            </div>
        `)

    }
}
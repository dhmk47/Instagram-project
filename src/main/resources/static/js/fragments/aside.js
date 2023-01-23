class EventSetter {
    static #instance = null;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new EventSetter();
        }

        return this.#instance;
    }

    constructor() {
        this.setAsideNavItemsClickEvent();
    }

    setAsideNavItemsClickEvent() {
        this.setHomeLiClickEvent();
        this.setSearchLiClickEvent();
        this.setExplorationLiClickEvent();
        this.setDirectMessageLiClickEvent();
        this.setAlertLiClickEvent();
        this.setCreateLiClickEvent();
        this.setUserProfileLiClickEvent();
        this.setMoreMenuDivClickEvnet();
        this.setInstagramLogoHoverEvent();
    }

    setHomeLiClickEvent() {
        $(".home-li").click(() => {
            this.initializationClass($(".home-li"));
            $("aside").removeClass("small-aside");
            $(".home-li span").addClass("select-nav-span");
        })
    }

    setSearchLiClickEvent() {
        $(".search-li").click(() => {
            this.initializationClass($(".search-li"));
            this.changeImageLogo();
            this.showSearchDiv();
            $("aside").addClass("small-aside");
            $(".search-li").addClass("select-nav-li");
            $(".aside-ul span, .more-menu-div span").addClass("visible").removeClass("select-nav-span");
            $(".search-li i").addClass("select-nav-i");
        })
    }

    setExplorationLiClickEvent() {
        $(".exploration-li").click(() => {
            this.initializationClass($(".exploration-li"));
            $("aside").removeClass("small-aside");
            $(".exploration-li span").addClass("select-nav-span");
        })
    }

    setDirectMessageLiClickEvent() {
        $(".direct-message-li").click(() => {
            this.initializationClass($(".direct-message-li"));
            $("aside").removeClass("small-aside");
            $(".direct-message-li span").addClass("select-nav-span");
        })
    }

    setAlertLiClickEvent() {
        $(".alert-li").click(() => {
            this.initializationClass($(".alert-li"));
            this.changeImageLogo();
            this.showAlertDiv();
            $("aside").addClass("small-aside");
            $(".alert-li").addClass("select-nav-li");
            $(".alert-li i").addClass("select-nav-i");
            $(".aside-ul span, .more-menu-div span").addClass("visible").removeClass("select-nav-span");
        })
    }

    setCreateLiClickEvent() {
        $(".create-li").click(() => {
            this.initializationClass($(".create-li"));
            $(".create-li").addClass("select-nav-span");
            $(".create-li i").addClass("select-nav-i");
            $(".main-modal-div").removeClass("visible");
            $("main").addClass("stop-scroll");
        })
    }

    setUserProfileLiClickEvent() {
        $(".user-profile-li").click(() => {
            this.initializationClass($(".user-profile-li"));
            $("aside").removeClass("small-aside");
            $(".user-profile-li span").addClass("select-nav-span");
        })
    }

    setMoreMenuDivClickEvnet() {
        $(".more-menu-div").click(() => {
            this.initializationClass($(".more-menu-div"));
            $(".more-menu-div span").addClass("select-nav-span");
            $(".more-menu-div i").addClass("select-nav-i");
        })
    }

    initializationClass(domOjbect) {
        let visibleFlag = this.setVisibleFlag(domOjbect);
        this.changeCharLogo();
        this.hideSearchDiv();
        this.hideAlertDiv();
        $("main").removeClass("stop-scroll");
        $(".aside-ul li").removeClass("select-nav-li");
        $(".more-menu-div span").removeClass("select-nav-span");
        $(".more-menu-div i").removeClass("select-nav-i");
        
        $(".aside-ul div i").removeClass("select-nav-i");
        $(".aside-ul span, .more-menu-div span").removeClass(`select-nav-span ${visibleFlag ? '': 'visible'}`);
    }

    setVisibleFlag(domOjbect) {
        return domOjbect.hasClass("create-li") || domOjbect.hasClass("more-menu-div");
    }

    changeImageLogo() {
        $(".logo-div").html(`<i class="fa-brands fa-instagram"></i>`);
        $(".logo-div").addClass("change-logo");
    }

    changeCharLogo() {
        $(".logo-div").html(`<img src="/static/image/logos/instagram-char-log.png" alt="인스타그램 문자 로고">`);
        $(".logo-div").removeClass("change-logo");
    }

    setInstagramLogoHoverEvent() {
        $(".logo-div").hover(() => {
            if($(".logo-div").hasClass("change-logo")) {
                $(".logo-div").css("backgroundColor", "#efefef");
            }
        },
            () => {
                $(".logo-div").css("backgroundColor", "white");
            }
        )
    }

    showSearchDiv() {
        $(".main-search-div").removeClass("visible");
    }

    hideSearchDiv() {
        $(".main-search-div").addClass("visible");
    }

    showAlertDiv() {
        $(".main-alert-div").removeClass("visible");
    }

    hideAlertDiv() {
        $(".main-alert-div").addClass("visible");
    }
}

class Search {
    static #instance = null;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new Search();
        }

        return this.#instance;
    }

    constructor() {
        this.setSearchEvent();
    }

    setSearchEvent() {
        const delay = 100;

        $(".search-input").keydown(function() {
            setTimeout(() => {
                if(!Search.getInstance().isEmpty($(this).val())) {
                    Search.getInstance().searchUserList($(this).val());
    
                }

            },delay)

        })
    }

    searchUserList(searchValue) {
        $.ajax({
            async: false,
            type: "get",
            url: `/api/v1/user/list/search?searchValue=${searchValue}`,
            contentType: "application/json",
            dataType: "json",
            success: (response) => {
                this.setSearchUserValue(response.data);
            },
            error: (request, status, error) => {
                console.log(request.status);
                console.log(request.responseText);
                console.log(error);
            }
        })
    }

    setSearchUserValue(searchValue) {
        $(".search-result-list-div").removeClass("flex-justice-align-center").html(`
            <ul class="search-result-list-ul">
                               
            </ul>
        `);

        searchValue.forEach(value => {
            const userProfileImage = value.userDetail.userProfileImage == null ? 'github-logo.png' : value.userDetail.userProfileImage;
            $(".search-result-list-ul").append(`
                <li>
                    <div class="detail-information-div">
                        <img class="user-image" src="/image/profiles/${userProfileImage}" alt="프로필">
                        <div class="user-information">
                            <span class="user-nickname-span">${value.userNickname}</span>
                            <span class="user-name-span">${value.userName}</span>
                        </div>
                    </div>
                </li>
            `);
        })
    }

    isEmpty(data) {
        return data == "" || data == undefined || data == null;
    }

    showRecentSearchUser() {
        $(".search-notice-div").removeClass("visible");

    }

    hideRecentSearchUser() {
        $(".search-notice-div").addClass("visible");
    }
}
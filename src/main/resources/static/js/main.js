window.onload = () => {
    EventSetter.getInstance();
}

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
            $("aside").addClass("small-aside");
            $(".aside-ul span").addClass("visible").removeClass("select-nav-span");
            $(".search-li div").addClass("select-nav-li");
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
            $("aside").addClass("small-aside");
            $(".alert-li div").addClass("select-nav-li");
            $(".alert-li i").addClass("select-nav-i");
            $(".aside-ul span").addClass("visible").removeClass("select-nav-span");
        })
    }

    setCreateLiClickEvent() {
        $(".create-li").click(() => {
            this.initializationClass($(".create-li"));
            $(".create-li").addClass("select-nav-text");
            $(".create-li i").addClass("select-nav-i");
            
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
        $(".search-li").click(() => {
            $("aside").addClass("small-aside");
            $(".search-li div").addClass("select-nav-li");
            $(".search-li i").addClass("select-nav-i");
        })
    }

    initializationClass(domOjbect) {
        $(".aside-ul div").removeClass("select-nav-li");
        $(".aside-ul div i").removeClass("select-nav-i");
        $(".aside-ul span").removeClass(`select-nav-span ${domOjbect.hasClass("create-li") ? '': 'visible'}`);
    }
}
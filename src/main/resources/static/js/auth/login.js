window.onload = () => {
    ButtonClickEventSetter.getInstance();
}

class ButtonClickEventSetter {
    static #instance = null;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new ButtonClickEventSetter();
        }

        return this.#instance;
    }

    constructor() {
        this.setLoadSignUpPageButtonClickEvent();
    }

    setLoadSignUpPageButtonClickEvent() {
        $(".load-sign-up-page-button").click(() => {
            location.href = "/sign-up";
        });
    }
}
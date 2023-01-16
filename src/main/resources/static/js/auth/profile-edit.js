window.onload = () => {
    AlertSetter.getInstance();
}

class AlertSetter {
    static #instance = null;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new AlertSetter();
        }

        return this.#instance;
    }

    constructor() {
        this.setAllAlertSettingButtonClickEvent();
        this.setAlertSettingButtonClickEvent();
    }

    setAllAlertSettingButtonClickEvent() {
        $(".all-alert-setting-input").change(function() {
            if($(this).is(":checked")) {
                $(".on-off-outer-label").addClass("on");
                $(".on-off-div").addClass("move-right");
            }else {
                $(".on-off-outer-label").removeClass("on");
                $(".on-off-div").removeClass("move-right");
            }
        })
    }

    setAlertSettingButtonClickEvent() {
        const radioInputItems = document.querySelectorAll(".radio-div input");
        const outerLabelItems = document.querySelectorAll(".radio-div label");

        radioInputItems.forEach(mainRadioInput => {
            mainRadioInput.onchange = () => {
                radioInputItems.forEach((radioInput, index) => {
                    if(radioInput.checked) {
                        outerLabelItems[index].classList.add("on");
                    }else {
                        outerLabelItems[index].classList.remove("on");
                    }
                })
            }
        })
    }
}
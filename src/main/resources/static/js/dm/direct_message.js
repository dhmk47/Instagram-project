window.onload = () => {
    SearchPeopleModal.getInstance();
}

class SearchPeopleModal {
    static #instance = null;

    removeFlag = false;

    checkInputList = null;
    checkLabelList = null;
    nicknameList = null;
    peopleList = null;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new SearchPeopleModal();
        }

        return this.#instance;
    }

    constructor() {
        this.setSendMessageButtonClickEvent();
        this.setModalCancelButtonClickEvent();
        this.setPeopleClickEvent();
        this.setInputSearchEvent();
    }

    setSendMessageButtonClickEvent() {
        $(".send-message-button").click(() => {
            $(".modal-outer-div").removeClass("visible");
        })
    }

    setModalCancelButtonClickEvent() {
        $(".search-modal-cancel-button").click(() => {
            this.initializationModal();
        })
    }

    initializationModal() {
        $(".modal-outer-div").addClass("visible");
        $(".result-main-div").empty();
        $(".search-input").val("");

        this.checkInputList.forEach(input => {
            input.checked = false;
        })
        this.checkLabelList.forEach(label => {
            label.classList.remove("select-people");
        })
    }

    setPeopleClickEvent() {
        this.peopleList = document.querySelectorAll(".search-result-people-list-div .people-ul li");
        const userNicknameList = document.querySelectorAll(".search-result-people-list-div .user-nickname-span");
        this.checkInputList = document.querySelectorAll(".people-check-input");
        this.checkLabelList = document.querySelectorAll(".people-check-label");

        this.peopleList.forEach((people, index) => {
            people.onclick = () => {
                if(this.checkInputList[index].checked) {
                    this.checkInputList[index].checked = false;
                    this.checkLabelList[index].classList.remove("select-people");
                    this.setResultUserNickname(userNicknameList[index].textContent);

                }else {
                    this.checkInputList[index].checked = true;
                    this.checkLabelList[index].classList.add("select-people");
                    this.setResultUserNickname(userNicknameList[index].textContent);

                }
            }
        })
    }

    setResultUserNickname(userNickname) {
        this.removeFlag = false;

        this.removeUserNicknameIfAlreadySelectedUser(userNickname);
        
        if(!this.removeFlag) {
            this.appendUserNicknameDivBox(userNickname);
            this.setResultCancelButtonClickEvent();
        }
        
    }

    removeUserNicknameIfAlreadySelectedUser(userNickname) {
        const resultDivList = document.querySelectorAll(".result-div");
        this.nicknameList = document.querySelectorAll(".result-div .user-nickname-span");

        this.nicknameList.forEach((nickname, index) => {
            if(nickname.textContent == userNickname) {
                const parentNode = resultDivList[index].parentNode;
                parentNode.removeChild(resultDivList[index]);

                this.removeFlag = true;
            }
        })
    }
    
    appendUserNicknameDivBox(userNickname) {
        $(".result-main-div").append(`
            <div class="result-div">
                <span class="user-nickname-span">${userNickname}</span>
                <i class="fa-solid fa-x result-cancel-button"></i>
            </div>
        `);
    }
    
    setResultCancelButtonClickEvent() {
        const resultCancelButtonItems = document.querySelectorAll(".result-cancel-button");
        
        resultCancelButtonItems.forEach(cancelButton => {
            cancelButton.onclick = () => {
                const selectedUser = cancelButton.parentNode.querySelector("span").textContent;
                this.peopleList.forEach(people => {
                    const searchResultUser = people.querySelector(".user-nickname-span").textContent;

                    if(selectedUser == searchResultUser) {
                        people.click();
                    }
                })
            }
        })
    }

    setInputSearchEvent() {
        const delay = 100;
        $(".search-input").change(function() {
            setTimeout(() => {
                console.log($(".search-input").val());
            }, delay)
        })
    }

}
window.onload = () => {
    Validator.getInstance();
    SignUpManager.getInstance();
}

class Validator {
    static #instance = null;

    userPhoneNumberAndEmailInput = null;
    userPasswordInput = null;

    userPhoneNumberAndEmailRegPassFlag = false;
    userPhoneNumberRegPassFlag = false;
    userEmailRegPassFlag = false;
    userPasswordRegPassFlag = false;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new Validator();
        }

        return this.#instance;
    }

    constructor() {
        this.userPhoneNumberAndEmailInput = document.querySelector(".user-phone-number-and-email-input");

        this.checkPhoneNumberRegExp();
        this.checkPasswordExp();
    }

    checkPhoneNumberRegExp() {
        const phoneReg = /^(010)-[0-9]{3,4}-[0-9]{3,4}$/;
        let userPhoneNumber = null;

        this.userPhoneNumberAndEmailInput.onblur = () => {
            if(userPhoneNumber != this.userPhoneNumberAndEmailInput.value) {
                userPhoneNumber = this.userPhoneNumberAndEmailInput.value;
    
                if(phoneReg.test(userPhoneNumber)) {
                    console.log(this);
                    this.userPhoneNumberAndEmailRegPassFlag = true;
                    this.userPhoneNumberRegPassFlag = true;

                }else {
                    this.userPhoneNumberRegPassFlag = false;
                    this.checkEmailRegExp();

                }
            }
        }
    }

    checkEmailRegExp() {
        const emailList = ["naver", "gmail", "daum", "nate", "kakao"];
        const emailReg = new RegExp(/^[\d\w]+@[\w]+.com$/, 'g');
        let userEmail = null;

        if(userEmail != this.userPhoneNumberAndEmailInput.value) {
            userEmail = this.userPhoneNumberAndEmailInput.value;

            const email = userEmail.substring(userEmail.indexOf("@") + 1, userEmail.lastIndexOf("."));

            if(emailReg.test(userEmail)) {
                if(!emailList.includes(email)) {
                    alert("지원하지 않는 이메일입니다.\n지원하는 이메일: naver, gmail, daum, nate, kakao");

                }else {
                    this.userPhoneNumberAndEmailRegPassFlag = true;
                    this.userEmailRegPassFlag = true;

                }

            }else {
                alert("올바르지 않은 형식입니다.");
                this.userEmailRegPassFlag = false;
                this.userPhoneNumberAndEmailRegPassFlag = false;
                this.userPhoneNumberAndEmailInput.focus();

            }
        }

    }

    checkPasswordExp() {
        this.userPasswordInput = document.querySelector(".user-password-input");
        const passwordReg = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[\d])(?=.*[!@#$*])[A-Za-z\d!@#$*]{8,14}$/;
        let userPassword = null;

        this.userPasswordInput.onblur = () => {
            if(userPassword != this.userPasswordInput.value) {
                userPassword = this.userPasswordInput.value;
    
                if(passwordReg.test(userPassword)) {
                    this.userPasswordRegPassFlag = true;
    
                }else {
                    alert("올바르지 않은 형식입니다.");
                }
            }
            
        }
    }

    validateData() {
        if(!this.userPhoneNumberAndEmailRegPassFlag) {
            alert("휴대폰 또는 이메일을 정확하게 입력해주세요.");
            $(".user-phone-number-and-email-input").focus();
            return false;

        }else if(this.isEmpty($(".user-name-input").val())) {
            alert("성명을 입력해주세요");
            $(".user-name-input").focus();
            return false;

        }else if(this.isEmpty($(".user-nickname-input").val())) {
            alert("사용자 이름을 입력해주세요.");
            $(".user-nickname-input").focus();
            return false;

        }else if(!this.userPasswordRegPassFlag) {
            alert("비밀번호의 형식을 지켜주세요.");
            $(".user-password-input").focus();
            return false;
        }

        return true;
    }

    isEmpty(data) {
        return data == "" || data == undefined || data == null;
    }
}

class SignUpManager {
    static #instance = null;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new SignUpManager();
        }

        return this.#instance;
    }

    constructor() {
        this.setSignUpClickEvent();
    }

    setSignUpClickEvent() {
        const signUpButton = document.querySelector(".sign-up-button");

        signUpButton.onclick = () => this.signUp();
    }
    
    signUp() {
        if(Validator.getInstance().validateData()) {
            const signUpData = this.getSignUpData();

            $.ajax({
                async: false,
                url: '/api/v1/auth/sign-up',
                type: "post",
                contentType: "application/json",
                data: JSON.stringify(signUpData),
                dataType: "json",
                success: (response) => {
                    if(response.data) {
                        alert("회원가입 성공");
                        location.replace("/sign-in");
                    }
                },
                error: (request, status, error) => {
                    console.log(request.status);
                    console.log(request.responseText);
                    console.log(error);
                }
            })
        }

    }

    getSignUpData() {
        const userId = Validator.getInstance().userPhoneNumberAndEmailInput.value;
        let signUpData = {
            "userId": userId,
            "userPassword": Validator.getInstance().userPasswordInput.value,
            "userName": $(".user-name-input").val(),
            "userNickname": $(".user-nickname-input").val(),
            "userEmail": Validator.getInstance().userEmailRegPassFlag ? userId : null,
            "userPhoneNumber": Validator.getInstance().userPhoneNumberRegPassFlag ? userId : null
        }

        return signUpData;
    }
}
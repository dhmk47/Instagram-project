window.onload = () => {
    RegExpObejct.getInstance();
}

class RegExpObejct {
    static #instance = null;

    userPhoneNumberRegPassFlag = false;
    userEmailRegPassFlag = false;
    userPasswordRegPassFlag = false;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new RegExpObejct();
        }

        return this.#instance;
    }

    constructor() {
        this.checkPhoneNumberRegExp();
        this.checkPasswordExp();
    }

    checkPhoneNumberRegExp() {
        const phoneReg = /^(010)-[0-9]{3,4}-[0-9]{3,4}$/;
        let userPhoneNumber = null;

        $(".user-phone-number-and-email-input").blur(function() {
            if(userPhoneNumber != $(this).val()) {
                userPhoneNumber = $(this).val();
    
                if(phoneReg.test(userPhoneNumber)) {
                    this.userPhoneNumberRegPassFlag = true;

                }else {
                    RegExpObejct.getInstance().checkEmailRegExp();
                    // alert("올바르지 않은 형식입니다.");
                    // $(this).focus();

                }
            }
        })
    }

    checkEmailRegExp() {
        const emailList = ["naver", "gmail", "daum", "nate", "kakao"];
        const emailReg = new RegExp(/^[\d\w]+@[\w]+.com$/, 'g');
        let userEmail = null;

        if(userEmail != $(".user-phone-number-and-email-input").val()) {
            userEmail = $(".user-phone-number-and-email-input").val();

            const email = userEmail.substring(userEmail.indexOf("@") + 1, userEmail.lastIndexOf("."));

            if(emailReg.test(userEmail)) {
                if(!emailList.includes(email)) {
                    alert("지원하지 않는 이메일입니다.\n지원하는 이메일: naver, gmail, daum, nate, kakao");

                }else {
                    this.userEmailRegPassFlag = true;

                }

            }else {
                alert("올바르지 않은 형식입니다.");
                $(".user-phone-number-and-email-input").focus();

            }
        }
    }

    checkPasswordExp() {
        const passwordReg = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[\d])(?=.*[!@#$*])[A-Za-z\d!@#$*]{8,14}$/;
        let userPassword = null;

        $(".user-password-input").blur(function() {
            if(userPassword != $(this).val()) {
                userPassword = $(this).val();
    
                if(passwordReg.test(userPassword)) {
                    this.userPasswordRegPassFlag = true;
    
                }else {
                    alert("올바르지 않은 형식입니다.");
                }
            }

        })
    }
}
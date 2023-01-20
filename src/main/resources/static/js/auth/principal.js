window.onload = () => {
    Principal.getInstance();
}

class Principal {
    static #instance = null;

    user = null;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new Principal();
        }

        return this.#instance;
    }

    constructor() {
        this.getPrincipal();
    }

    getPrincipal() {
        $.ajax({
            async: false,
            type: "get",
            url: "/api/v1/auth/principal",
            dataType: "json",
            success: (response) => {
                this.user = response.data;
            },
            error: (request, status, error) => {
                console.log(request.status);
                console.log(request.responseText);
                console.log(error);
            }
        })
    }
}
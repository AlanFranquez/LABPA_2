const formulario = document.querySelector("#myform");
const botonSubmit = document.querySelector(".boton-Reg");

formulario.addEventListener("input", () => {
    let completos = true;

    const nick = formulario.querySelector("#nick");
    const nombre = formulario.querySelector("#nombre");
    const apellido = formulario.querySelector("#apellido");
    const email = formulario.querySelector("#email");
    const fechaNacimiento = formulario.querySelector("#fecha");
    const compania = formulario.querySelector("#compania");
    const web = formulario.querySelector("#web");
    const password = formulario.querySelector("#password");
    const passwordConf = formulario.querySelector("#passwordConf");

    if (
        nick.value.trim() === "" ||
        nombre.value.trim() === "" ||
        apellido.value.trim() === "" ||
        email.value.trim() === "" ||
        fechaNacimiento.value.trim() === "" ||
        compania.value.trim() === "" ||
        web.value.trim() === "" ||
        password.value.trim() === "" ||
        passwordConf.value.trim() === ""
    ) {
        completos = false;
    }

    if (password.value !== passwordConf.value) {
        completos = false;
    }

    const emailPattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;
    if (!email.value.match(emailPattern)) {
        completos = false;
    }

    botonSubmit.disabled = !completos;
});

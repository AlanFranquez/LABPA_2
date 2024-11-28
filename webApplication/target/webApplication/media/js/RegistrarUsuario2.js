const formulario = document.querySelector("#myform");
const botonSubmit = document.querySelector(".boton-Reg");

formulario.addEventListener("input", () => {
	
	let completos = true;
	const nombre  = formulario.querySelector("#nombre");
	const apellido = formulario.querySelector("#apellido");
	const img = formulario.querySelector("#imagen");
	const fechaNacimiento = formulario.querySelector("#nacimiento");
	
	if(nombre.value.trim() === "" || apellido.value.trim() === "" || fechaNacimiento.value.trim() === "") {
		completos = false;
	}
	botonSubmit.disabled = !completos;
});

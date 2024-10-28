const formulario = document.querySelector("#myform");
const botonSubmit = document.querySelector(".boton-Reg");

formulario.addEventListener("input", () => {
	
	const inputs = formulario.querySelectorAll("input");
	let completos = true;
	
	inputs.forEach(input => {
		if(input.value.trim() === "") {
			completos = false;
		}
	});
	botonSubmit.disabled = !completos;
});

const form = document.getElementById("form")

form.addEventListener('submit', verifyForm, false)

function verifyForm (e) {
    if (!form.checkValidity()) {
        e.preventDefault()
        e.stopPropagation()
    }
    form.classList.add('was-validated')
}
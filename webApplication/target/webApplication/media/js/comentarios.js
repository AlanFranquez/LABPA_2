// Cargar comentarios al cargar la página
    document.addEventListener("DOMContentLoaded", function() {
        loadComments();
    });

    // Función para enviar el comentario usando AJAX
    function submitComment() {
        const commentText = document.getElementById("commentText").value;
        const prodId = '<%= prod.getId() %>';  // Asume que el producto tiene un ID único

        if (commentText.trim() === "") {
            alert("El comentario no puede estar vacío.");
            return;
        }

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "EnviarComentario", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                // Limpiar el textarea
                document.getElementById("commentText").value = "";
                // Recargar los comentarios sin recargar la página
                loadComments();
            }
        };

        xhr.send("comment=" + encodeURIComponent(commentText) + "&prodId=" + prodId);
    }

    // Función para cargar los comentarios
    function loadComments() {
        const prodId = '<%= prod.getId() %>';  // Asume que el producto tiene un ID único

        const xhr = new XMLHttpRequest();
        xhr.open("GET", "LoadCommentsServlet?prodId=" + prodId, true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                document.getElementById("commentSection").innerHTML = xhr.responseText;
            }
        };
        xhr.send();
    }
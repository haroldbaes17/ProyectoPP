document.addEventListener('DOMContentLoaded', function () {
    iniciarApp();
});

function iniciarApp() {
    selectPaises();
    mensajesExito();
    eliminarProductoConfirmacion()
}

function selectPaises() {
    const selectPais = document.getElementById("paisSelect");

    if (!selectPais) return; // Verifica que el select exista

    fetch("https://restcountries.com/v3.1/all?fields=translations")
        .then(response => response.json())
        .then(data => {
            const paisesOrdenados = data
                .map(pais => pais.translations.spa.common)
                .sort((a, b) => a.localeCompare(b));

            paisesOrdenados.forEach(pais => {
                let option = document.createElement("option");
                option.value = pais;
                option.textContent = pais;
                selectPais.appendChild(option);
            });
        })
        .catch(error => console.error("Error al obtener los países:", error));
}

function mensajesExito() {
    setTimeout(function () {
        let alert = document.querySelector(".alert");
        if (alert) {
            alert.classList.remove("show");
            alert.classList.add("fade");
            setTimeout(() => alert.remove(), 500); // Elimina el elemento del DOM después de la animación
        }
    }, 3000);
}

function eliminarProductoConfirmacion() {
    document.querySelectorAll(".eliminar-btn").forEach(button => {
        button.addEventListener("click", function(event) {
            event.preventDefault(); // Evita la navegación inmediata

            const url = this.getAttribute("data-href"); // Obtiene la URL del botón

            Swal.fire({
                title: "¿Estás seguro?",
                text: "Esta acción no se puede deshacer.",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#d33",
                cancelButtonColor: "#8ebb53",
                confirmButtonText: "Sí, eliminar",
                cancelButtonText: "Cancelar"
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = url; // Redirige si confirma
                }
            });
        });
    });
}
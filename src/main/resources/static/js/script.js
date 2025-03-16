document.addEventListener('DOMContentLoaded', function () {
    iniciarApp();
});

function iniciarApp() {
    selectPaises();
    mensajesExito();
    eliminarProductoConfirmacion()
    formatearPrecios()
    selectTalla()
    cambiarCantidad()
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
        let alerta = document.querySelector(".alerta");
        if (alerta) {
            alerta.classList.remove("show");
            alerta.classList.add("fade");
            setTimeout(() => alerta.remove(), 500); // Elimina el elemento del DOM después de la animación
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

function formatearPrecios() {
    document.querySelectorAll("[id^='precio']").forEach(precioElement => {
        let precioTexto = precioElement.innerText.replace("₡ ", ""); // Quitar símbolo ₡
        let precioNumero = parseFloat(precioTexto).toFixed(2); // Asegurar dos decimales

        // Formatear el número con punto como separador de miles y coma para decimales
        precioNumero = precioNumero.replace(/\B(?=(\d{3})+(?!\d))/g, " ").replace(" ", ",");

        precioElement.innerText = `₡ ${precioNumero}`;
    });
}


function selectTalla() {
    const sizeOptions = document.querySelectorAll(".size-option");
    const selectedSizeInput = document.getElementById("selectedSize");

    sizeOptions.forEach(option => {
        option.addEventListener("click", function () {
            sizeOptions.forEach(opt => opt.classList.remove("active")); // Remueve la clase activa de todos
            this.classList.add("active"); // Agrega la clase activa al seleccionado
            selectedSizeInput.value = this.dataset.size; // Actualiza el valor oculto
        });
    });
}

function cambiarCantidad() {
    const decreaseBtn = document.getElementById("decrease");
    const increaseBtn = document.getElementById("increase");
    const quantityInput = document.getElementById("cantidad");

    // Asegurar que la cantidad siempre inicie en 1 si no es válida
    if (!quantityInput.value || isNaN(quantityInput.value) || parseInt(quantityInput.value) < 1) {
        quantityInput.value = 1;
    }

    decreaseBtn.addEventListener("click", function (event) {
        event.preventDefault(); // Evita que el formulario se envíe
        let currentValue = parseInt(quantityInput.value);
        if (currentValue > 1) {
            quantityInput.value = currentValue - 1;
        }
    });

    increaseBtn.addEventListener("click", function (event) {
        event.preventDefault(); // Evita que el formulario se envíe
        let currentValue = parseInt(quantityInput.value);
        if (currentValue < 100) {
            quantityInput.value = currentValue + 1;
        }
    });
}
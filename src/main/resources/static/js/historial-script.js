document.addEventListener('DOMContentLoaded', function () {
    iniciarApp();
});

function iniciarApp() {
    formatearPrecios()
    formatearFecha()
}

function formatearPrecios() {
    // Selecciona todos los elementos que tengan la clase .currency-format
    const currencyElements = document.querySelectorAll('.currency-format');

    currencyElements.forEach(function(element) {
        // Toma el texto, por ejemplo: "₡ 145000.00"
        let rawValue = element.textContent || '';

        // Elimina todo lo que NO sea dígito o punto (por si lleva símbolo de moneda, espacios, etc.)
        rawValue = rawValue.replace(/[^\d.]/g, '');
        // Ahora rawValue debería ser "145000.00"

        // Convierte a número
        const numberValue = parseFloat(rawValue);

        // Si se pudo parsear, formateamos
        if (!isNaN(numberValue)) {
            // Formato estilo "en-US": usa coma para miles y punto para decimales
            const formatted = new Intl.NumberFormat('en-US', {
                minimumFractionDigits: 2,
                maximumFractionDigits: 2
            }).format(numberValue);

            // Actualiza el contenido con "₡ " más el número formateado
            element.textContent = `₡ ${formatted}`;
        }
    });
}

function formatearFecha() {
    // Selecciona todos los spans que contengan la fecha en formato YYYY-MM-DD
    const fechaElements = document.querySelectorAll(".fecha-iso");

    fechaElements.forEach(elem => {
        const fechaTexto = elem.innerText.trim();
        // Verificamos que cumpla el patrón YYYY-MM-DD
        if (/^\d{4}-\d{2}-\d{2}$/.test(fechaTexto)) {
            const [year, month, day] = fechaTexto.split("-");
            // Reemplazamos el texto con el formato deseado
            elem.innerText = `${day}-${month}-${year}`;
        }
    });
}
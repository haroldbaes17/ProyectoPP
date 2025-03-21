document.addEventListener('DOMContentLoaded', function () {
    iniciarApp();
});

function iniciarApp() {

}

// Función para cambiar de formulario y marcar el botón seleccionado
function changeForm(formId, button) {
    $('.profile-container').hide();
    $(formId).show();
    $('.btn-group-vertical button').removeClass('active');
    $(button).addClass('active');
}

// Al cargar la página, se muestra la información de usuario y se selecciona su botón
changeForm('#formUserInfo', '#btnUserInfo');

$('#btnUserInfo').click(function(){
    changeForm('#formUserInfo', this);
});
$('#btnUpdateProfile').click(function(){
    changeForm('#formUpdateProfile', this);
});
$('#btnUpdateAddress').click(function(){
    changeForm('#formUpdateAddress', this);
});
$('#btnOrderHistory').click(function(){
    changeForm('#formOrderHistory', this);
});


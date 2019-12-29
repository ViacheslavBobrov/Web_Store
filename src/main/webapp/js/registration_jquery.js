$('#submit_button').click(function(){
	
    checkAllFields();    
});

function checkAllFields() {

    var submit = true;
    var handlerMap = {
        'name_error': isNameValid(),
        'surname_error': isSurnameValid(),
        'email_error': isEmailValid(),
        'firstPassword_error': isPasswordValid(),
        'secondPassword_error': isPasswordsEqual()
    }
    for(var error in handlerMap) {
        if (!handlerMap[error]) {
            $('#' + error).show();
            submit = false;
        } else {
            $('#' + error).hide();
        }
    }
    if (!submit) {
        event.preventDefault();
    }
}

function isNameValid() {
    var regexp = /^[a-zA-Zа-яА-Я0-9]{2,25}$/;
    return regexp.test($('#name').val());
}

function isSurnameValid() {
    var regexp = /^[a-zA-Zа-яА-Я0-9]{2,25}$/;
    return regexp.test($('#surname').val());
}

function isEmailValid() {
    var regexp = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return regexp.test($('#email').val());
}

function isPasswordValid() {
    var regexp = /^[a-zA-Z0-9]{5,25}$/;
    return regexp.test($('#firstPassword').val());
}

function isPasswordsEqual() {
    return $('#firstPassword').val() == $('#secondPassword').val();
}

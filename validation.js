function validateEmail() {
    var e = document.getElementById("email").value.split('');
    var atCount = 0;
    var valid = true;
    for (i in e) {
        console.log(e[i]);
        if (e[i] === "@") {
            atCount = atCount + 1;
        } else if ((e[i] >= "0" && e[i] <= "9") || (e[i] >= "a" && e[i] <= "z") || (e[i] == '.')) {
            // Do Nothing 
            // Valid character
        } else {
            valid = false;
            break;
        }
    }
    if (atCount > 1 || atCount == 0)
        valid = false;
    console.log(atCount);
    if (atCount == 1 && valid) {
        var atIndex = document.getElementById("email").value.indexOf("@");
        var dotIndex = document.getElementById("email").value.lastIndexOf(".");
        console.log(atIndex);
        console.log(dotIndex);
        if (dotIndex - atIndex <= 0 || dotIndex == document.getElementById("email").value.length) {
            valid = false;
        }
    }
    if (!valid) {
        alert("Enter valid email ID");
        document.getElementById("email").value = '';
    }
}

function validateConfirmPassword(){
    if(document.getElementById("password").value != document.getElementById("confirm_password").value){
        alert("Both passwords should match");
        document.getElementById("confirm_password").value = "";
    }
}
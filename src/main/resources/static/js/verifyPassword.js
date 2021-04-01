/**
   * Validate if the provided information is valid. This includes a filled username as well as
   * matching passwords. We can set password standards here as well (string length/ character variety).
   * Form will submit if these fields are valid and the backend should ensure that the account is unique.
**/

function validateForm(){
    var username = document.forms["submitForm"]["username"].value;
    var password = document.forms["submitForm"]["password"].value;
    var password2 = document.forms["submitForm"]["password2"].value;

    if(username === "" || password1 === "" || password2 === ""){
    alert("error");
    return false;
    }else if(password != password2){
    alert("passwords do not match");
    return false;
    }

    return true;
}
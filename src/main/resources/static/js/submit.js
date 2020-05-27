window.addEventListener( "load", function (){



 const form = document.getElementById("signinForm");

    grecaptcha.ready(() =>{
            grecaptcha.execute( "6LeGXvcUAAAAAHiQ1GdoL-gGqPAOCPiwog60wHdu", {action: 'submit'})
            .then(function(token) {

                recaptchaResponse.value = token;
                form.appendChild(recaptchaResponse);
            });

        });
//    form.addEventListener("submit", function(event){
//        event.preventDefault();
//        function sendData(){
//                const XHR = new XMLHttpRequest();
//                const formData = new FormData(form);
//
//                XHR.open("POST", "/login");
//
//                XHR.send(formData);
//            }
//        sendData();
//    } );
} );
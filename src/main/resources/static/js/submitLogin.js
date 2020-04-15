
        $(document).ready(function(){
            $('#signinForm').submit(function (event){

                var formData = {
                username : $("input#username").val(),
                password : $("input#password").val()
                };

                console.log(JSON.stringify(formData));
                console.log($("input"));
                    $.ajax({
                        type: 'POST',
                            headers: {
                                'Content-Type' : 'application/json'
                                },
                            url: '/login',
                            data: JSON.stringify(formData),
                            success: function(output, status, xhr){
                                   console.log(xhr.getResponseHeader("Authorization"));
                            }
                    });
            });
        });
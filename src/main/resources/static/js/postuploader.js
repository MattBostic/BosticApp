
 $(function() {
     // Get the form.
     var form = $('#form-signin');

     // Get the messages div.
     var formData = {
          username : $("input#username").val(),
          password : $("input#password").val()
          };
        $.ajax({
                 type: 'POST',
                     headers: {
                         'Content-Type' : 'application/json'
                         },
                     url: '/login',
                     data: JSON.stringify(formData),
                     success: function(output, status, xhr){
                            console.log(username);

                     }
             });
 });


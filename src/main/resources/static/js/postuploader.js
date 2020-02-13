function postPost(){

         const url = 'http://localhost/posts'

         const payload = {
             method : 'POST',
             headers: {
                 'Accept': 'application/json',
                 'Content-Type': 'application/json;charset=UTF-8'
                 },
                 body: JSON.stringify({
                 body: 'This post was created on the frontend',
                 imgURL: 'Another Decker pic.'
                 })

             };

         fetch(url, payload)
         .then(response => {
                 console.log(response.status);
         });

 }
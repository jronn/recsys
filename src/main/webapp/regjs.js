/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function validate() {
    var letters = /^[A-Za-z]+$/;
    var lettersnumbers = /^[0-9a-zA-Z]+$/;
    var emailvalid = /\S+@\S+\.\S+/;
    if(document.myForm["myForm:name"].value.length < 2 
       || document.myForm["myForm:name"].value.length > 20
       || !document.myForm["myForm:name"].value.match(letters)){
        alert("Please enter the name correctly. \n\
               Minimum characters: 2 \n\
               Maximum characters: 20 \n\
               Names must only contain letters.");
        return false;
    }
    if(document.myForm["myForm:surname"].value.length < 2 
       || document.myForm["myForm:surname"].value.length > 20
       || !document.myForm["myForm:surname"].value.match(letters)){
        alert("Please enter the surname correctly. \n\
               Minimum characters: 2 \n\
               Maximum characters: 20 \n\
               Surnames must only contain letters.");
        return false;
    }
    if(!document.myForm["myForm:email"].value.match(emailvalid)){
        alert("Please enter the email correctly. \n\
               Example: test@hotmail.com");
        return false;
    }
    if(document.myForm["myForm:username"].value.length < 4 
       || document.myForm["myForm:username"].value.length > 20
       || !document.myForm["myForm:username"].value.match(lettersnumbers)){
        alert("Please enter the username correctly. \n\
               Minimum characters: 4 \n\
               Maximum characters: 20 \n\
               Username must only contain letters and numbers.");
        return false;
    }
    if(document.myForm["myForm:password"].value.length < 6 
       || document.myForm["myForm:password"].value.length > 20
       || !document.myForm["myForm:password"].value.match(lettersnumbers)
       || document.myForm["myForm:password"].value === document.myForm["myForm:username"].value){
        alert("Please enter the password correctly. \n\
               Minimum characters: 6 \n\
               Maximum characters: 20 \n\
               Username must only contain letters and numbers.\n\
               and can not be the same as the username.");
        return false;
    }
    return true;
}



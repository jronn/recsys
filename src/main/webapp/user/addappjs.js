/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function validateYears(){
    var years = document.getElementById("years");
    if(years > 70 || isNan(years) || years <= 0){
        alert("Enter valid input.");
        return false;
    }
    return true;
}
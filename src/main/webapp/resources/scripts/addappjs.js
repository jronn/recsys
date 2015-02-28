/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function validateExpertise() {
    var years = document.getElementById('addForm:years').value;
    var expertise = document.getElementById('addForm:expertise').value;
    if (expertise === null) {
        alert('Choose a valid expertise.');
        return false;
    } else if (isNaN(years) || years <= 0 || years > 50) {
        alert('Insert a valid number in the years of experience text field.');
        return false;
    } else {
        alert('Expertise: ' + expertise + ',\nYears of Experience: ' + years
                + '\n added to your list.');
        return true;
    }
}

function validateDates() {
    var from = document.getElementById('addForm:from').value;
    var to = document.getElementById('addForm:to').value;
    date_pattern = /^\d{4}\-\d{1,2}\-\d{1,2}$/;

    if (!date_pattern.test(from)) {
        alert('From date is faulty.');
        return false;
    }

    if (!date_pattern.test(to)) {
        alert('To date is faulty.');
        return false;
    }

    var fromparts = from.split("-");
    var fromday = parseInt(fromparts[2], 10);
    var frommonth = parseInt(fromparts[1], 10);
    var fromyear = parseInt(fromparts[0], 10);

    var toparts = to.split("-");
    var today = parseInt(toparts[2], 10);
    var tomonth = parseInt(toparts[1], 10);
    var toyear = parseInt(toparts[0], 10);

    if (fromyear < 1930 || fromyear > 2100 || frommonth <= 0 || frommonth > 12
            || fromday <= 0 || fromday > 31) {
        alert('Date pattern for the from date is correct but the date is not available.');
        return false;
    }

    if (toyear < 1930 || toyear > 2100 || tomonth <= 0 || tomonth > 12
            || today <= 0 || today > 31) {
        alert('Date pattern for the to date is correct but the date is not available.');
        return false;
    }

    var startDate = new Date(from);
    var endDate = new Date(to);

    if (startDate > endDate) {
        alert('From date cannot be before to date.');
        return false;
    }

    alert('Dates added to the list.');
    return true;
}

function onlyNumbers(){
    if(event.charCode >= 48 && event.charCode <= 57){
        return true;
    } else {
        return false;
    }
}
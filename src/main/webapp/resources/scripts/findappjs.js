function validate() {
    var name = document.getElementById('findForm:name').value;
    var surname = document.getElementById('findForm:surname').value;
    var reg = document.getElementById('findForm:regDate').value;
    var from = document.getElementById('findForm:fromDate').value;
    var to = document.getElementById('findForm:toDate').value;
    var date_pattern = /^\d{4}\-\d{1,2}\-\d{1,2}$/;
    var letters = /^[A-Za-z]+$/;


    if (!name.match(letters) && name !== "") {
        alert('Name must only contain letters.');
        return false;
    }
    
    if (!surname.match(letters) && surname !== "") {
        alert('Surname must only contain letters.');
        return false;
    }
    
    if (!date_pattern.test(reg) && reg !== "") {
        alert('Registration date is faulty. Must contain a date or be left empty.\n\
                Example: 2014-05-05');
        return false;
    }

    if (!date_pattern.test(from) && from !== "") {
        alert('From date is faulty. Must contain a date or be left empty.\n\
                Example: 2000-01-01');
        return false;
    }

    if (!date_pattern.test(to) && to !== "") {
        alert('To date is faulty. Must contain a date or be left empty.\n\
                Example: 2001-01-01');
        return false;
    }
    
    if((from === "" && to !== "") ||(from !== "" && to === "")){
        alert('From or to date not filled in. Either leave the fields empty or\n\
            fill them both.');
        return false;
    }
    
    var startDate = new Date(from);
    var endDate = new Date(to);

    if (startDate > endDate) {
        alert('From date cannot be before to date.');
        return false;
    }

    return true;
}

jsf.ajax.addOnEvent(function(data) {
    if (data.status === "success") {
        var viewState = getViewState(data.responseXML);

        for (var i = 0; i < document.forms.length; i++) {
            var form = document.forms[i];

            if (form.method === "post" && !hasViewState(form)) {
                createViewState(form, viewState);
            }
        }
    }
});

function getViewState(responseXML) {
    var updates = responseXML.getElementsByTagName("update");

    for (var i = 0; i < updates.length; i++) {
        if (updates[i].getAttribute("id").match(/^([\w]+:)?javax\.faces\.ViewState(:[0-9]+)?$/)) {
            return updates[i].firstChild.nodeValue;
        }
    }

    return null;
}

function hasViewState(form) {
    for (var i = 0; i < form.elements.length; i++) {
        if (form.elements[i].name === "javax.faces.ViewState") {
            return true;
        }
    }

    return false;
}

function createViewState(form, viewState) {
    var hidden;

    try {
        hidden = document.createElement("<input name='javax.faces.ViewState'>"); // IE6-8.
    } catch (e) {
        hidden = document.createElement("input");
        hidden.setAttribute("name", "javax.faces.ViewState");
    }

    hidden.setAttribute("type", "hidden");
    hidden.setAttribute("value", viewState);
    hidden.setAttribute("autocomplete", "off");
    form.appendChild(hidden);
}
$(document).ready(function() {
    $.ajax({
        url: "http://localhost:9100/me/all"
    }).then(function(data) {
       $('.greeting-id').append(data.id);
       $('.greeting-content').append(data.username);
    });
});
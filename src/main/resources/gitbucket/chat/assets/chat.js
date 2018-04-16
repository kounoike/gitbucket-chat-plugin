function template(string, values){
    return string.replace(/\{\{(.*?)\}\}/g, function(all, key){
        return Object.prototype.hasOwnProperty.call(values, key) ? values[key] : "";
    });
}

var socket = atmosphere;
var request = {
    url: "chat-atmosphere",
    contentType: "application/json",
    logLevel: "debug",
    transport: "websocket",
    trackMessageLength: true,
    reconnectInterval: 5000
};
request.onOpen = function(response) {
    console.log("connect");
};
request.onMessage = function(response) {
    console.log("message");
    console.log(response);
    if (!response.responseBody.startsWith("X")) {
        var target =$("#chat-container");
        if (response.responseBody.startsWith("{")) {
            target.append(template($("#message-template").html(), JSON.parse(response.responseBody)));
        }
        else{
            target.append("<p>" + response.responseBody + "</p>");
        }
    }
};
var subSocket = socket.subscribe(request);
var inputMessage = $("#inputMessage");
inputMessage.keydown(function(e){
if (e.keyCode === 13){
    var msg = $(this).val();
    if (msg.length > 0){
        var json = {
            author: me,
            message: msg
        };
        subSocket.push(JSON.stringify(json));
    $(this).val('');
    }
}
});

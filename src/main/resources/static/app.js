let socket = new WebSocket("ws://localhost:8080/chat");

socket.onmessage = function(event) {
    let messageArea = document.getElementById("messageArea");
    messageArea.innerHTML += '<p>' + event.data + '</p>';

};

let send = document.getElementById("sendBtn");
send.onclick = sendMessage;

function sendMessage() {
    let username = document.getElementById("username").value;
    let messageInput = document.getElementById("messageInput");

    let fullMessage = username + ": " +  messageInput.value;
    socket.send(fullMessage);
    messageInput.value = '';
}
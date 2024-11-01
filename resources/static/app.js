let socket = new WebSocket("ws://localhost:8080/chat");

socket.onmessage = function(event) {
    let messageArea = document.getElementById("messageArea");
    messageArea.innerHTML += '<p>' + event.data + '</p>';

};

let send = document.getElementById("sendBtn");
send.onclick = sendMessage;

function sendMessage() {
    let messageInput = document.getElementById("messageInput");
    let message = messageInput.value.trim();

    if (message) {
        socket.send(message);
        messageInput.value = ''; 
    }
}
function makeMove(move) {
    alert(`Вы выбрали: ${move}`);
    // TODO: логику обработки хода
}

function sendMessage() {
    const input = document.getElementById('chatInput');
    const message = input.value;
    if (message) {
        const chatMessages = document.getElementById('chatMessages');
        const messageElement = document.createElement('div');
        messageElement.textContent = message;
        chatMessages.appendChild(messageElement);
        input.value = '';
        chatMessages.scrollTop = chatMessages.scrollHeight; // Прокрутка к последнему сообщению
    }
}

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

function loadFriendsList() {
    fetch('/api/friends', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            // Добавьте другие заголовки, если необходимо, например, для аутентификации
        },
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Ошибка при загрузке списка друзей');
        }
        return response.json();
    })
    .then(data => {
        const friendsList = document.getElementById('friendsList');
        friendsList.innerHTML = '';
        data.forEach(friend => {
            const friendItem = document.createElement('div');
            friendItem.className = 'friend-item';
            friendItem.innerHTML = `
                <span>${friend.username}</span>
                <div>
                    <button onclick="inviteToGame('${friend.username}')">Пригласить в игру</button>
                    <button onclick="viewProfile('${friend.username}')">Профиль игрока</button>
                    <button onclick="removeFriend('${friend.username}')">Удалить из друзей</button>
                </div>
            `;
            friendsList.appendChild(friendItem);
        });
    })
    .catch(error => {
        console.error('Ошибка:', error);
        alert('Ошибка при загрузке списка друзей');
    });
}


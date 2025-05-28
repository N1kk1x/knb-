let playerScore = 0;
let computerScore = 0;

// Локальная логика игры КНБ
function makeMove(playerMove) {
    const moves = ['rock', 'paper', 'scissors'];
    const computerMove = moves[Math.floor(Math.random() * moves.length)];

    const result = determineWinner(playerMove, computerMove);

    alert(`Вы выбрали: ${playerMove}\nКомпьютер выбрал: ${computerMove}\nРезультат: ${result}`);

    if (result === 'Победа') {
        playerScore++;
    } else if (result === 'Поражение') {
        computerScore++;
    }

    updateScoreboard();
}

function determineWinner(player, computer) {
    if (player === computer) {
        return 'Ничья';
    }
    if (
        (player === 'rock' && computer === 'scissors') ||
        (player === 'paper' && computer === 'rock') ||
        (player === 'scissors' && computer === 'paper')
    ) {
        return 'Победа';
    }
    return 'Поражение';
}

function updateScoreboard() {
    document.getElementById('playerScore')?.textContent = playerScore;
    document.getElementById('computerScore')?.textContent = computerScore;
}

// Отправка сообщения в чат
function sendMessage() {
    const input = document.getElementById('chatInput');
    const message = input.value.trim();
    if (message) {
        const chatMessages = document.getElementById('chatMessages');
        const messageElement = document.createElement('div');
        messageElement.textContent = message;
        chatMessages.appendChild(messageElement);
        input.value = '';
        chatMessages.scrollTop = chatMessages.scrollHeight;
    }
}

// Загрузка списка друзей
function loadFriendsList() {
    fetch('/api/friends', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
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

// Заглушки для приглашения в игру и просмотра профиля
function inviteToGame(username) {
    alert(`Вы пригласили игрока ${username} в игру. Функция в разработке.`);
}

function viewProfile(username) {
    alert(`Открытие профиля игрока ${username}. Функция в разработке.`);
}

function removeFriend(username) {
    // Здесь вы можете реализовать удаление друга через API
    alert(`Удаление ${username} из друзей. Функция в разработке.`);
}

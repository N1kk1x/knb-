let playerScore = 0;
let computerScore = 0;

// Функция доступна в глобальной области видимости
window.makeMove = function(playerMove) {
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
};

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
    const playerScoreElement = document.getElementById('playerScore');
    const computerScoreElement = document.getElementById('computerScore');

    if (playerScoreElement) {
        playerScoreElement.textContent = playerScore;
    }

    if (computerScoreElement) {
        computerScoreElement.textContent = computerScore;
    }
}

// Чат
window.sendMessage = function() {
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
};

// Друзья
window.loadFriendsList = function() {
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
};

// Заглушки
window.inviteToGame = function(username) {
    alert(`Вы пригласили игрока ${username} в игру. Функция в разработке.`);
};

window.viewProfile = function(username) {
    alert(`Открытие профиля игрока ${username}. Функция в разработке.`);
};

window.removeFriend = function(username) {
    alert(`Удаление ${username} из друзей. Функция в разработке.`);
};

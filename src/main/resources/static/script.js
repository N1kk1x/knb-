const API_BASE_URL = 'http://localhost:8080';

function makeMove(move) {
  alert(`Вы выбрали: ${move}`);
  // TODO: логику обработки хода
}

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

function loadProfileData() {
  fetch(`${API_BASE_URL}/api/users/me`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include',
  })
    .then(response => {
      if (!response.ok) {
        return response.text().then(text => {
          throw new Error(`Ошибка при загрузке данных профиля: ${response.status} ${text}`);
        });
      }
      return response.json();
    })
    .then(data => {
      document.getElementById('username').textContent = data.username;
      document.getElementById('email').textContent = data.email;
      document.getElementById('role').textContent = data.role;
      document.getElementById('matchesPlayed').textContent = data.matchesPlayed;
      document.getElementById('wins').textContent = data.wins;
      document.getElementById('eloRating').textContent = data.eloRating;
    })
    .catch(error => {
      console.error('Ошибка:', error);
      alert(error.message);
    });
}

function loadFriendsList() {
  fetch(`${API_BASE_URL}/api/friends`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include',
  })
    .then(response => {
      if (!response.ok) {
        return response.text().then(text => {
          throw new Error(`Ошибка при загрузке списка друзей: ${response.status} ${text}`);
        });
      }
      return response.json();
    })
    .then(data => {
      const friendsList = document.getElementById('friendsList');
      if (!friendsList) {
        console.warn('Элемент с id "friendsList" не найден.');
        return;
      }
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
      alert(error.message);
    });
}

function addFriend() {
  const friendName = document.getElementById('searchFriendInput').value.trim();
  if (!friendName) return;

  fetch(`${API_BASE_URL}/api/friends/add`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include',
    body: JSON.stringify({ friendName }),
  })
    .then(response => {
      if (!response.ok) {
        return response.text().then(text => {
          throw new Error(`Ошибка при добавлении друга: ${response.status} ${text}`);
        });
      }
      return response.json();
    })
    .then(data => {
      alert(`Друг ${friendName} добавлен!`);
      document.getElementById('searchFriendInput').value = '';
      loadFriendsList();
    })
    .catch(error => {
      console.error('Ошибка:', error);
      alert(error.message);
    });
}

document.addEventListener('DOMContentLoaded', () => {
  loadProfileData();
  loadFriendsList();

  const registerForm = document.getElementById('registerForm');
  if (registerForm) {
    registerForm.addEventListener('submit', function (event) {
      event.preventDefault();
      const username = document.getElementById('username').value.trim();
      const email = document.getElementById('email').value.trim();
      const password = document.getElementById('password').value.trim();

      fetch(`${API_BASE_URL}/api/auth/register`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, email, password }),
      })
        .then(response => {
          if (!response.ok) {
            return response.text().then(text => {
              throw new Error(`Ошибка при регистрации: ${response.status} ${text}`);
            });
          }
          return response.json();
        })
        .then(data => {
          alert('Регистрация прошла успешно!');
          window.location.href = 'login.html';
        })
        .catch(error => {
          console.error('Ошибка:', error);
          alert(error.message);
        });
    });
  }
});

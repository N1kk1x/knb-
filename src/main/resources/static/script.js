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


    fetch('/api/users/1')
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(error => console.error('Ошибка:', error));

    // Пример вызова метода getUserByUsername
    fetch('/api/users/by-username/johndoe')
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(error => console.error('Ошибка:', error));

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


     document.getElementById('registerForm').addEventListener('submit', function(event) {
          event.preventDefault();
          const username = document.getElementById('username').value;
          const email = document.getElementById('email').value;
          const password = document.getElementById('password').value;

          fetch('/api/auth/register', {
              method: 'POST',
              headers: {
                  'Content-Type': 'application/json',
              },
              body: JSON.stringify({ username, email, password }),
          })
          .then(response => response.json())
          .then(data => {
              alert('Регистрация прошла успешно!');
              window.location.href = 'login.html';
          })
          .catch(error => {
              console.error('Ошибка:', error);
              alert('Ошибка при регистрации');
          });
      });
     }


        function loadProfileData() {
            fetch('/api/users/me', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Ошибка при загрузке данных профиля');
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
                alert('Ошибка при загрузке данных профиля');
            });
        }

        // Функция добавления друга с проверкой ответа
        function addFriend() {
            const friendName = document.getElementById('searchFriendInput').value;
            if (friendName) {
                fetch('/api/friends/add', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ friendName }),
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Ошибка при добавлении друга');
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
                    alert('Ошибка при добавлении друга');
                });
            }
        }


     // Загружаем данные при загрузке страницы
      window.onload = function() {
          loadProfileData();
           loadFriendsList();
      };



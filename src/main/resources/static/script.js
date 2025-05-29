const API_BASE_URL = 'http://localhost:8080';

let mode = 'single';
let playerScore = 0;
let opponentScore = 0;
let draws = 0;

let stompClient = null;
let username = localStorage.getItem('username') || 'Гость';
let gameId = null;

let playerMoves = [];
let opponentMoves = [];

function setMode(selectedMode) {
  mode = selectedMode;
  alert(`Режим установлен: ${mode === 'single' ? 'Против компьютера' : 'PvP'}`);
  resetGame();
}

function makeMove(playerMove) {
  if (playerMoves.length >= 3) {
    alert("Вы уже выбрали 3 хода. Ожидайте завершения раунда.");
    return;
  }

  playerMoves.push(playerMove);
  document.getElementById('player-move').textContent = `Ваш выбор: ${playerMoves.map(translateMove).join(', ')}`;

  if (playerMoves.length === 3) {
    if (mode === 'single') {
      playVsComputer();
    } else {
      if (!stompClient || !stompClient.connected) {
        connectWebSocket(() => sendMove(playerMoves));
      } else {
        sendMove(playerMoves);
      }
    }
  }
}

function translateMove(move) {
  return move === 'rock' ? 'Камень' :
         move === 'paper' ? 'Бумага' :
         move === 'scissors' ? 'Ножницы' : '-';
}

// ==== PvC ====
function playVsComputer() {
  const choices = ['rock', 'paper', 'scissors'];
  opponentMoves = [];
  for (let i = 0; i < 3; i++) {
    opponentMoves.push(choices[Math.floor(Math.random() * choices.length)]);
  }
  document.getElementById('opponent-move').textContent = `Ход компьютера: ${opponentMoves.map(translateMove).join(', ')}`;

  let playerWins = 0;
  let opponentWins = 0;

  for (let i = 0; i < 3; i++) {
    const pMove = playerMoves[i];
    const oMove = opponentMoves[i];
    if (pMove === oMove) {
      // ничья
    } else if (
      (pMove === 'rock' && oMove === 'scissors') ||
      (pMove === 'paper' && oMove === 'rock') ||
      (pMove === 'scissors' && oMove === 'paper')
    ) {
      playerWins++;
    } else {
      opponentWins++;
    }
  }

  if (playerWins > opponentWins) {
    playerScore++;
  } else if (opponentWins > playerWins) {
    opponentScore++;
  } else {
    draws++;
  }

  updateScoreboard();
  resetMoves();
}

// ==== PvP ====
function connectWebSocket(callback) {
  const socket = new SockJS('/ws');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, () => {
    stompClient.subscribe('/topic/game/' + (gameId || 'default') + '/' + username, (message) => {
      const data = JSON.parse(message.body);
      document.getElementById('opponent-move').textContent = `Ход соперника: ${data.opponentMoves.map(translateMove).join(', ')}`;

      if (data.result === 'win') {
        playerScore++;
      } else if (data.result === 'lose') {
        opponentScore++;
      } else {
        draws++;
      }

      updateScoreboard();
      resetMoves();
    });

    if (callback) callback();
  });
}

function sendMove(moves) {
  stompClient.send('/app/play', {}, JSON.stringify({
    username,
    moves,
    gameId: gameId || 'default'
  }));
}

// ==== Обновление счёта ====
function updateScoreboard() {
  const scoreboard = document.getElementById('scoreboard');
  scoreboard.innerHTML = `
    <h3>Счёт</h3>
    <p>Вы: ${playerScore}</p>
    <p>Соперник: ${opponentScore}</p>
    <p>Ничьи: ${draws}</p>
  `;
}

function resetMoves() {
  playerMoves = [];
  opponentMoves = [];
  document.getElementById('player-move').textContent = 'Ваш выбор: -';
  document.getElementById('opponent-move').textContent = 'Ход соперника: -';
}

function resetGame() {
  playerScore = 0;
  opponentScore = 0;
  draws = 0;
  resetMoves();
  updateScoreboard();
}

// ==== Чат ====
function sendMessage() {
  const input = document.getElementById('chatInput');
  const message = input.value.trim();
  if (message) {
    const chatMessages = document.getElementById('chatMessages');
    const messageElement = document.createElement('div');
    messageElement.textContent = `${username}: ${message}`;
    chatMessages.appendChild(messageElement);
    input.value = '';
    chatMessages.scrollTop = chatMessages.scrollHeight;
  }
}

// ==== Логика показа подвала при прокрутке страницы вниз ====
window.addEventListener('scroll', () => {
  const scrollTop = window.scrollY || window.pageYOffset;
  const windowHeight = window.innerHeight;
  const documentHeight = document.documentElement.scrollHeight;

  const footer = document.querySelector('footer');
  if (!footer) return;

  if (scrollTop + windowHeight >= documentHeight) {
    footer.style.display = 'block';
  } else {
    footer.style.display = 'none';
  }
});

// При загрузке страницы сразу скрываем подвал
window.addEventListener('load', () => {
  const footer = document.querySelector('footer');
  if (footer) footer.style.display = 'none';
});

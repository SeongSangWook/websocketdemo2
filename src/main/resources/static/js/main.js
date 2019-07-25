'use strict';
// strict 모드 사용. 자바스크립트가 묵인했던 에러들의 에러 메시지 발생(엄격한 문법 검사)

// var contents = document.querySelector('#contents');

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var registerPage = document.querySelector('#register-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var registerForm = document.querySelector('#registerForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
// document객체의 메서드를 통해 객체 찾기

var socket = new SockJS('/ws');
// server 소켓의 endpoint인 /ws로 접속할 클라이언트  소켓 생성.
var stompClient = null;
stompClient = Stomp.over(socket);

var sessionUser = null;
var name = null;
// WebSocket은 웹 상에서 쉽게 소켓통신을 하게 해주는 라이브러리
// 스프링에서는 SockJS 라이브러리와 STOMP 프로토콜을 사용 가능
// SockJS는 WebSocket 기능 보완, 향상

// STOMP는 메시지 전송을 효율적으로 지원
// 일반 WebSocket 환경은 핸들러만 구현해주고 직접 호출함
// STOMP 이용시 핸들러와 브로커라는 개념으로 서로간 통신함

// STOMP:Simple/Streaming Text Oriented Messaging Protocol의 약자, 텍스트 기반 메시징 프로토콜
// 구독이라는 개념을 통해 내가 통신하고자 하는 주체(topic)를 판단하여 실시간, 지속적으로 관심을 가진다.
// MesssageHandler에서 해당 요청시 처리 과정을 구현

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];
// 색깔코드 배열

function connect(event) {
	// 웹소켓으로 서버 접속
    var id = document.querySelector('#userid').value.trim();
    var pw = document.querySelector('#userpw').value.trim();
    
    /*
    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');
        stompClient.connect({}, onConnected, onError);
        socket = new SockJS('/ws');
        // /ws/{방번호}
        // server 소켓의 endpoint인 /ws로 접속할 클라이언트  소켓 생성.
        stompClient = Stomp.over(socket);
        // stomp에 소켓 적용
 		
        
    }
    */
    var user = {
    		userId: id, 
    		userPw: pw	
    };
    stompClient.send("/app/loginUser",
        {},
        JSON.stringify(user)
    )
    sessionUser = sessionStorage.getItem(user);
    if(sessionUser)
    	name = sessionUser.name;
    
    // 현재 이벤트의 기본 동작을 중단한다.
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);
    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: name, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
	// 받은 메시지를 해석해서 메시지 창에 나오는 HTML 소스 추가
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);
        // 아이콘 만들기

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
        // username 띄우기
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);
    // 메시지 내용 띄우기

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
    // 추가하고, 스크롤 제일 밑으로 내리기
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
    // 아바타 색 랜덤 지정
}
function goToRegister() {
    usernamePage.classList.add("hidden");
    registerPage.classList.remove('hidden');
    
}
function register() {
	var registerForm=document.getElementById("registerForm"); //폼 name
	
	var registerid = document.querySelector('#registeruserid').value.trim();
    var registerpw = document.querySelector('#registeruserpw').value.trim();
    var registername = document.querySelector('#registername').value.trim();
    
    var user = {
        userId: registerid,
        userPw: registerpw,
        name: registername
    };

    // Tell your username to the server
    stompClient.send("/app/registerUser",
        {},
        JSON.stringify(user)
    )
    
    connectingElement.classList.add('hidden');
    // registerForm.formUser = user; 
    // registerForm.action="";//이동할 페이지
    // registerForm.method="post";//POST방식
    // registerForm.submit();
}

$(document).ready(function(){
	$("#usernameForm button").click(function (event) {
		event.preventDefault();
		if ($(this).attr("value") == "button1") {             
			connect();
		}
		else if ($(this).attr("value") == "button2") {
			goToRegister();
		}
	});
	
	if(sessionUser!=null){
		usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');
        stompClient.connect({}, onConnected, onError);
	}
});

// usernameForm.addEventListener('submit', connect, true);
messageForm.addEventListener('submit', sendMessage, true);
registerForm.addEventListener('submit', register, true);
// 버튼 EventListener 설정
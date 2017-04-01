window.onload = function () {
    ip = '10.37.0.35';
    port = '80';

    socket = new WebSocket('ws://'+ip+':'+port);
    
    // Listen for messages
    socket.addEventListener('message', function (event) {
	console.log('Message from server', event.data);
    });

    socket.addEventListener('open', function (event) {
	console.log("Socket Open");
	leftButton = document.getElementById("left");	
	rightButton = document.getElementById("right");
	setUpListeners();
    });
};

var socket;
var leftButton;
var rightButton;
var leftDown = true;
var rightDown =false;
var left = "left";
var right = "right";
var forward = "forward";

function setUpListeners() {
    leftButton.addEventListener('mousedown',function(event) {
	leftDown = true;

	if (rightDown) {
	    socket.send(forward);
	} else {
	    socket.send(left);
	}
    });

    rightButton.addEventListener('mousedown',function(event) {
	rightDown = true;

	if (leftDown) {
	    socket.send(forward);
	} else {
	    socket.send(right);
	}
    });

    leftButton.addEventListener('mouseup',function(event) {
	leftDown = false;

	if (rightDown) {
	    socket.send(right);
	} else {
	    socket.send(forward);
	}
    });

    rightButton.addEventListener('mouseup',function(event) {
	rightDown = false;

	if (leftDown) {
	    socket.send(left);
	} else {
	    socket.send(forward);
	}
    });			   
			    
}

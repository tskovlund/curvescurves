window.onload = function () {
    let ip = '10.37.0.64';
    let port = '80';

    socket = new WebSocket('ws://'+ip+':'+port);
    
    // Listen for messages
    socket.addEventListener('message', function (event) {
	console.log('Message from server', event.data);
    });

    socket.addEventListener('open', function (event) {
	alert("Connection Established");
	leftButton = document.getElementById("left");	
	rightButton = document.getElementById("right");
	if (/iPhone|iPad|iPod|Android/i.test(navigator.userAgent)) {
	    setUpListeners('touchend','touchstart');
	} else {
	    setUpListeners('mouseup','mousedown');
	}
    });
<<<<<<< HEAD

    socket.addEventListener('error', function(error) {
	alert("Connection Failed");
    });
}			   
=======
};
>>>>>>> ee7712e55651bf2fdaeed326070b3a96cfd63a24

var socket;
var leftButton;
var rightButton;
var leftDown = false;
var rightDown = false;
var left = "left";
var right = "right";
var forward = "forward";

function setUpListeners(up,down) {    
    leftButton.addEventListener(down,function(event) {
	leftDown = true;

	if (rightDown) {
	    socket.send(forward);
	} else {
	    socket.send(left);
	}
    });

    rightButton.addEventListener(down,function(event) {
	rightDown = true;

	if (leftDown) {
	    socket.send(forward);
	} else {
	    socket.send(right);
	}
    });

    leftButton.addEventListener(up,function(event) {
	leftDown = false;

	if (rightDown) {
	    socket.send(right);
	} else {
	    socket.send(forward);
	}
    });

    rightButton.addEventListener(up,function(event) {
	rightDown = false;

	if (leftDown) {
	    socket.send(left);
	} else {
	    socket.send(forward);
	}
    });			   
			    
}

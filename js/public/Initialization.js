window.onload = function () {
    let ip = '10.37.0.64';
    let port = '8080';

    socket = new WebSocket('ws://' + ip + ':' + port);

    // Listen for messages
    socket.addEventListener('message', function (event) {
        console.log('Message from server', event.data);
    });

    socket.addEventListener('open', function (event) {
        connectionEstablished();
        leftButton = document.getElementById("left");
        rightButton = document.getElementById("right");
        if (/iPhone|iPad|iPod|Android/i.test(navigator.userAgent)) {
            setUpListeners('touchend', 'touchstart');
        } else {
            setUpListeners('mouseup', 'mousedown');
        }
    });

    socket.addEventListener('error', function (error) {
        alert("Connection Failed");
    });
};

var socket;
var leftButton;
var rightButton;
var leftDown = false;
var rightDown = false;
var left = "left";
var right = "right";
var forward = "forward";

function setUpListeners(up, down) {
    leftButton.addEventListener(down, function (event) {
        leftDown = true;

        if (rightDown) {
            socket.send(forward);
        } else {
            socket.send(left);
        }
    });

    rightButton.addEventListener(down, function (event) {
        rightDown = true;

        if (leftDown) {
            socket.send(forward);
        } else {
            socket.send(right);
        }
    });

    leftButton.addEventListener(up, function (event) {
        leftDown = false;

        if (rightDown) {
            socket.send(right);
        } else {
            socket.send(forward);
        }
    });

    rightButton.addEventListener(up, function (event) {
        rightDown = false;

        if (leftDown) {
            socket.send(left);
        } else {
            socket.send(forward);
        }
    });
}

function connectionEstablished() {
    // Get the snackbar DIV
    var x = document.getElementById("snackbar")

    // Add the "show" class to DIV
    x.className = "show";

    // After 3 seconds, remove the show class from DIV
    setTimeout(function () {
        x.className = x.className.replace("show", "");
    }, 3000);
}

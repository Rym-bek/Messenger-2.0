const WebSocket = require('ws');
const webSocketController = require("./webSocketController");

function initWebSocketServer(server) {
    const wss = new WebSocket.Server({ server });

    wss.on('connection', function connection(ws, request) {
        const uid = request.headers['uid'];
        if (uid) {
            webSocketController.addClient(uid, ws);
            webSocketController.sendOfflineStatus(true, uid)
        }
        else {
            ws.close();
        }

        ws.on('message', function incoming(message) {
            const data = JSON.parse(message);
            const senderUid = data.sender_uid;
            const roomUid = data.room_uid;
            const messageData = data.message;
            console.log("senderUid:", senderUid)
            console.log("roomUid:", roomUid)
            console.log("messageData:", messageData)
            if (!roomUid || !senderUid || !messageData) {
                console.error("Missing parameter");
            }
            webSocketController.sendPrivateMessage(senderUid, roomUid, messageData);
        });

        ws.on('close', function () {
            const uid = webSocketController.getWebSocketUid(ws);
            console.log(uid);
            webSocketController.deleteClient(ws);
            webSocketController.sendOfflineStatus(false, uid);

        });
    });
}

module.exports = {
    initWebSocketServer,
};

const clients = new Map();

const webSocketDatabase = require("./webSocketDatabase");


function sendOfflineStatus(online, uid) {
    clients.forEach(function (client) {
        client.send(JSON.stringify({
            uid: uid,
            online: online,
        }));
    });
}

function addClient(uid, ws) {
    clients.set(uid, ws);
    console.log(`Клиент зарегистрирован: ${uid}`);
}

function getWebSocketClient(userId) {
    return clients.get(userId);
}

function getWebSocketUid(ws) {
    for (let [uid, client] of clients.entries()) {
        if (client === ws) {
            console.log(uid);
            return uid;
        }
    }
    return null;
}

function sendMessage(clientUid, senderUid, roomUid, message, time) {
    const date = new Date(time)
    const formattedDate = date.toISOString();
    const client = getWebSocketClient(clientUid);
    if (client) {
        client.send(JSON.stringify({
            sender_uid: senderUid,
            room_uid: roomUid,
            message: message,
            time: formattedDate
        }));
    } else {
        console.error(`Client ${clientUid} not found`);
    }
}

async function sendPrivateMessage(senderUid, roomUid, message) {
    const time = Date.now();
    try {
        const users = await webSocketDatabase.getUsersFromRoom(roomUid);
        users.forEach(function (object) {
            sendMessage(object.user_uid, senderUid, roomUid, message, time);
        });
    } catch (error) {
        console.error(error);
    }

    webSocketDatabase.saveMessage(senderUid, roomUid, message, time);
    console.log(`Сообщение от ${senderUid} к ${roomUid}: ${message}`);
}

function deleteClient(ws) {
    clients.forEach(function (client, uid) {
        if (client === ws) {
            clients.delete(uid);
            console.log(`Клиент отключился: ${uid}`);
        }
    });
}

module.exports = {
    sendOfflineStatus,
    getWebSocketUid,
    addClient,
    sendPrivateMessage,
    deleteClient,
    clients,
}
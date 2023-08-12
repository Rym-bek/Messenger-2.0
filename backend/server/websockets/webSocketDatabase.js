const Room = require('../models/room');
const Messages = require('../models/message');
const Participants = require('../models/participants');

async function roomExists(room_uid) {
    const object = await Room.findOne({
        where: {
            uid: room_uid
        }
    })

    if (object === null) {
        return false;
    }
    return true;
}

async function saveRoom(uid) {
    if (!await roomExists(uid)) {
        await Room.create({
            uid: uid,
        });
    }
}

async function saveMessage(senderUid, roomUid, message, time) {
    const messageObject = {
        sender_uid: senderUid,
        room_uid: roomUid,
        message: message,
        time: time
    };
    Messages.create(messageObject);
}

async function saveUserInRoom(roomUid, userUid) {
    console.log("User saved in Room")
    await Participants.create({
        user_uid: userUid,
        room_uid: roomUid
    });
}

async function getUsersFromRoom(roomUid) {
    try {
        const results = await Participants.findAll({
            where: {
                room_uid: roomUid
            },
            attributes: ['user_uid'],
            raw: true,
        });
        return results;
    } catch (error) {
        console.error(error);
        throw error;
    }
}



module.exports = {
    saveRoom,
    saveMessage,
    saveUserInRoom,
    roomExists,
    getUsersFromRoom
}
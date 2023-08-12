const Message = require('../models/message');
const webSocketController = require("../websockets/webSocketController");
const uuid = require("uuid");

const webSocketDatabase = require("../websockets/webSocketDatabase");


const { sequelize, QueryTypes } = require('../utils/sequelize');
async function getAllDialogs(request, response) {
    const userUid = request.query.userUid
    if (!userUid) {
        response.status(400).json({ error: "Missing userUid parameter" });
    }
    try {
        const messages = await sequelize.query(`
   SELECT
                rooms.uid AS room_uid,
                participants.user_uid AS another_uid,
                self.user_uid AS my_uid,
                users.firstname AS firstname,
                users.nickname AS nickname,
                users.photo_url AS photo_url,
                messages.message AS message,
                messages.time AS time
            FROM
                participants
                INNER JOIN users ON participants.user_uid = users.uid
                INNER JOIN rooms ON participants.room_uid = rooms.uid
                INNER JOIN (
                    SELECT
                        room_uid,
                        MAX(time) AS max_time
                    FROM
                        messages
                    GROUP BY
                        room_uid
                ) last_message ON rooms.uid = last_message.room_uid
                INNER JOIN messages ON last_message.room_uid = messages.room_uid AND last_message.max_time = messages.time
                INNER JOIN participants AS self ON self.room_uid = participants.room_uid
            WHERE
                self.user_uid = :userUid
                AND participants.user_uid != :userUid
            ORDER BY
                messages.time DESC
`, {
            type: QueryTypes.SELECT,
            replacements: { userUid: userUid },
        });

        //статус онлайн
        const messagesWithStatus = messages.map((message) => {
            const isOnline = webSocketController.clients.get(message.another_uid);
            return {
                ...message,
                online: isOnline ? true : false,
            };
        });

        //webSocketController.sendOnlineStatus(true, messagesWithStatus);

        response.status(200).json(messagesWithStatus);
    }
    catch (error) {
        console.error(error);
        response.status(500).json({ error: "Internal server error" });
    }
}

async function createPrivateRoom(participants) {
    roomUid = uuid.v4();
    await webSocketDatabase.saveRoom(roomUid);
    for (const participant of participants) {
        await webSocketDatabase.saveUserInRoom(roomUid, participant);
        console.log(`клиент добавлен в комнату: ${roomUid},${participant} `);
    }
    console.log(`Комната создана: ${roomUid}`);
    return roomUid;
}

async function createRoom(request, response) {
    const participants = request.body.participants;
    if (!participants) {
        response.status(400).json({ error: "Missing parameter" });
        return;
    }
    try {
        const roomUid = await createPrivateRoom(participants);
        console.log(roomUid);
        response.status(200).json({ room_uid: roomUid });
    }
    catch (error) {
        console.error(error);
        response.status(500).json({ error: "Internal server error" });
    }
}

async function findMessages(roomUid) {
    try {
        const results = await Message.findAll({
            attributes: ['sender_uid', 'room_uid', 'message', 'time'],
            order: [
                ['id', 'DESC'],
            ],
            where: {
                room_uid: roomUid
            },
            limit: 100,
        })
        return results.reverse();
    } catch (error) {
        console.error(error);
        throw error;
    }
}

async function getPrivateMessages(request, response) {
    const roomUid = request.query.roomUid;
    if (!roomUid) {
        response.status(400).json({ error: "Missing roomUid parameter" });
    }
    try {
        const messages = await findMessages(roomUid);
        response.status(200).json(messages);
    }
    catch (error) {
        console.error(error);
        response.status(500).json({ error: "Internal server error" });
    }
}

module.exports = {
    getAllDialogs,
    getPrivateMessages,
    createRoom
}

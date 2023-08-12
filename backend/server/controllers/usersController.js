const User = require('../models/user');
const { sequelize, QueryTypes } = require('../utils/sequelize');

const findUsers = async (userUid, nickname) => {
    try {
        const users = await sequelize.query(`
    SELECT p.room_uid AS room_uid, u.uid as another_uid, u.nickname, u.firstname, u.lastname, u.photo_url
FROM users u
LEFT JOIN participants p ON p.user_uid = u.uid AND p.room_uid IN (
    SELECT room_uid
    FROM participants
    WHERE user_uid = :userUid
)
WHERE u.nickname LIKE :nickname AND u.uid!= :userUid
  `, {
            replacements: {
                userUid: userUid,
                nickname: nickname + '%'
            },
            type: QueryTypes.SELECT
        });
        return users;
    } catch (error) {
        console.error(error);
    }
}

const findUser = async (userUid) => {
    try {
        return await User.findOne({
            attributes: ['nickname', 'firstname', 'lastname', 'photo_url'],
            where: {
                uid: userUid
            }
        });
    } catch (error) {
        console.error(error);
    }
}

const getUserByUid = async (request, response) => {
    const userUid = request.query.userUid;
    if (!userUid) {
        response.status(500).json({ error: "Internal server error" });
    }
    try {
        const user = await findUser(userUid);
        response.status(200).json(user);
    }
    catch (error) {
        console.error(error);
    }
}

const getUsers = async (request, response) => {
    const nickname = request.query.nickname;
    const userUid = request.query.userUid;
    if (!nickname || !userUid) {
        response.status(500).json({ error: "Internal server error" });
    }
    try {
        const user = await findUsers(userUid, nickname);
        response.status(200).json(user);
    }
    catch (error) {
        console.error(error);
    }
}

module.exports = {
    getUsers,
    getUserByUid,
}
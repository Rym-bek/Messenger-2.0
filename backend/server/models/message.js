const { DataTypes } = require('sequelize')
const { sequelize } = require("../utils/sequelize");

const Messages = sequelize.define('messages',
    {
        id: {
            type: DataTypes.INTEGER,
            primaryKey: true,
            autoIncrement: true,
            allowNull: false
        },
        sender_uid: {
            type: DataTypes.UUID,
            allowNull: false,
            unique: true
        },
        room_uid: {
            type: DataTypes.UUID,
            allowNull: false,
            unique: true
        },
        message: {
            type: DataTypes.STRING,
            allowNull: false
        },
        time: {
            type: DataTypes.DATE,
        },
    },
    {
        timestamps: false
    }
)

module.exports = Messages
const { DataTypes } = require('sequelize')
const { sequelize } = require("../utils/sequelize");

const Participants = sequelize.define('participants',
    {
        id: {
            type: DataTypes.INTEGER,
            primaryKey: true,
            autoIncrement: true,
            allowNull: false
        },
        user_uid: {
            type: DataTypes.UUID,
            allowNull: false,
            unique: true
        },
        room_uid: {
            type: DataTypes.STRING(80),
            allowNull: false,
            unique: true
        },
    },
    {
        timestamps: false
    }
)

module.exports = Participants
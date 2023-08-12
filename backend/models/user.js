const { DataTypes } = require('sequelize')
const { sequelize } = require("../utils/sequelize");

const Users = sequelize.define('users',
    {
        id: {
            type: DataTypes.INTEGER,
            primaryKey: true,
            autoIncrement: true,
            allowNull: false
        },
        uid: {
            type: DataTypes.UUID,
            allowNull: false,
            unique: true
        },
        email: {
            type: DataTypes.STRING(100),
            unique: true
        },
        password: {
            type: DataTypes.STRING(100),
        },
        phone: {
            type: DataTypes.STRING(15),
            unique: true
        },
        nickname: {
            type: DataTypes.STRING(50),
            unique: true
        },
        photo_url: {
            type: DataTypes.STRING(150),
        },
        firstname: {
            type: DataTypes.STRING(50),
        },
        lastname: {
            type: DataTypes.STRING(50),
        },
        about: {
            type: DataTypes.STRING(150),
        },
        photo_id: {
            type: DataTypes.STRING(20),
        },
    },
    {
        timestamps: false
    }
)

module.exports = Users
const { DataTypes } = require('sequelize')
const { sequelize } = require("../utils/sequelize");

const Rooms = sequelize.define('rooms',
    {
        id: {
            type: DataTypes.INTEGER,
            primaryKey: true,
            autoIncrement: true,
            allowNull: false
        },
        uid: {
            type: DataTypes.STRING(80),
            allowNull: false,
            unique: true
        },
        name: {
            type: DataTypes.STRING(50),
        },
    },
    {
        timestamps: false
    }
)

module.exports = Rooms
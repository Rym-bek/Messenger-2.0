const { DataTypes } = require('sequelize')
const { sequelize } = require("../utils/sequelize");
const User = require('./user');

const Location = sequelize.define('locations',
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
            references: {
                model: User,
                key: 'uid'
            }
        },
        location: {
            type: DataTypes.GEOMETRY('POINT', 4326),
            allowNull: true
        },
        last_updated: {
            type: DataTypes.DATE,
            allowNull: true
        },
        enabled: {
            type: DataTypes.BOOLEAN,
            allowNull: true
        }
    }, {
    tableName: 'locations',
    timestamps: false
});


module.exports = Location;
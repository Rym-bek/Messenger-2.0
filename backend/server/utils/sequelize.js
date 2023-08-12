const { Sequelize, QueryTypes } = require('sequelize')
const Op = require('sequelize').Op
require('dotenv').config();

const sequelize = new Sequelize(
    process.env.PG_DATABASE,
    process.env.PG_USER,
    process.env.PG_PASSWORD, {
    host: process.env.PG_HOST,
    dialect: 'postgres',
    pool: {
        max: 20,
        min: 0,
        acquire: 30000,
        idle: 10000
    }
})

const connect = async () => {
    try {
        await sequelize.authenticate();
        console.log('Sequlize connected');
    } catch (error) {
        console.error('No connection', error);
    }
}

module.exports = {
    connect,
    sequelize,
    Op,
    QueryTypes,
}

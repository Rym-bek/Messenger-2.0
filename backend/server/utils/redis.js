const { createClient } = require('redis');

const redisClient = createClient();

redisClient.on("error", function (err) {
    console.log("Error " + err);
});

redisClient.on("connect", function () {
    console.log("Redis client connected");
});

//redisClient.connect().catch(console.error);

module.exports = redisClient;
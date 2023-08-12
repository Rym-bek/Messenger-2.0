const router = require("express").Router();
const messageController = require("../controllers/messageController");
const tokenManager = require('../utils/token');

router.get("/", tokenManager.verifyToken, async (request, response) => {
    messageController.getAllDialogs(request, response)
});

router.get("/private", tokenManager.verifyToken, async (request, response) => {
    messageController.getPrivateMessages(request, response)
});

router.post("/createRoom", tokenManager.verifyToken, async (request, response) => {
    messageController.createRoom(request, response)
});

module.exports = router;




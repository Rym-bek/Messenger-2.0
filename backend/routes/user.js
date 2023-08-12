const router = require("express").Router();
const userController = require("../controllers/userController");
const tokenManager = require('../utils/token');

router.get("/", tokenManager.verifyToken, async (request, response) => {
    userController.getUser(request, response)
});

router.post("/update", tokenManager.verifyToken, async (request, response) => {
    userController.updateUser(request, response)
});

module.exports = router;
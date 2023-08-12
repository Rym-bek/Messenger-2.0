const router = require("express").Router();
const registerController = require("../controllers/registerController.js");

router.post("/", async (request, response) => {
    registerController.registerUser(request, response)
});

module.exports = router;

const router = require("express").Router();
const loginController = require("../controllers/loginController");

router.post("/", async (request, response) => {
    loginController.loginUser(request, response)
});

module.exports = router;
const router = require("express").Router();
const usersController = require("../controllers/usersController");

router.get("/", async (request, response) => {
    usersController.getUsers(request, response)
});

router.get("/uid", async (request, response) => {
    usersController.getUserByUid(request, response)
});


module.exports = router;
const uuid = require("uuid");
const tokenManager = require('../utils/token');

const { emailExists } = require('../controllers/controllerUtils');

const User = require('../models/user');

const registerUser = async (request, response) => {
    var postData = request.body;
    var email = postData.email;
    var password = postData.password;

    const user = {
        uid: uuid.v4(),
        email: email,
        password: password
    };

    if (!await emailExists(email)) {
        const token = tokenManager.generateToken({ email, password })
        if (token) {
            await User.create(user)
                .then(response.status(200).json({ token: token }))
                .catch(error => response.status(400).send(error))
        }
        else {
            response.json(false);
        }
    }
    else {
        response.json(false);
    }
}

module.exports = {
    registerUser,
}
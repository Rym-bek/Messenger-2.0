const tokenManager = require('../utils/token')
const { emailExists } = require('../controllers/controllerUtils');
const User = require('../models/user');

const loginUser = async (request, response) => {
    var postData = request.body;
    var email = postData.email;
    var password = postData.password;

    if (await emailExists(email)) {
        await User.findOne({
            where: {
                email: email
            },
            attributes: ['password'],
        }).then(results => {
            if (results.password == password) {
                response.status(200).json({ token: tokenManager.generateToken({ email, password }) })
            }
        }).catch((error) => {
            console.error(error);
        });
    }
    else {
        response.json(false);
    }
}

module.exports = {
    loginUser,
}

const User = require('../models/user');

const findUser = async (email) => {
    return await User.findOne({ where: { email: email } });
}

const getUser = async (request, response) => {
    var email = request.userInfo;
    if (!email) {
        response.status(500).json(false);
    }
    try {
        const user = await findUser(email);
        response.status(200).json(user);
    }
    catch (error) {
        console.error(error);
    }
}

const updateUser = async (request, response) => {
    var email = request.userInfo;
    if (!email) {
        response.status(500).json(false);
    }
    let user = null;
    try {
        user = await findUser(email);
    }
    catch (error) {
        console.error(error);
    }

    if (user) {
        var postData = request.body;
        var nickname = postData.nickname;
        var firstname = postData.firstname;
        var lastname = postData.lastname;
        var about = postData.about;
        if (email != null) {
            if (nickname != null) {
                user.nickname = nickname;
            }
            if (firstname != null) {
                user.firstname = firstname;
                if (lastname != null) {
                    user.lastname = lastname;
                }
            }
            if (about != null) {
                user.about = about;
            }
        }
        await user.save();
        response.status(200).json(user);
    }
    else {
        response.status(500).json(false);
    }
}

module.exports = {
    getUser,
    updateUser,
}
const User = require('../models/user');

const emailExists = async (email) => {
    const project = await User.findOne({
        where: {
            email: email
        }
    })

    if (project === null) {
        return false;
    }
    return true;
}

module.exports = {
    emailExists,
}
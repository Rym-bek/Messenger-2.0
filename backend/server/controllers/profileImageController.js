const User = require('../models/user');
const cloudinary = require("../utils/cloudinary");

const profilePhoto = async (url, email, publicId) => {
    await User.update({
        photo_url: url,
        photo_id: publicId
    }, {
        where: {
            email: email
        }
    }).catch((error) => {
        console.error(error);
    });
}

const findPhoto = async (email) => {
    return await User.findOne({
        attributes: ['photo_id'],
        where: {
            email: email
        },
    }).catch((error) => {
        console.error(error);
    });
}

const deletePhoto = async (email) => {
    const public_id = await findPhoto(email);
    console.log(public_id.dataValues.photo_id);
    cloudinary.deleteCloudinary(public_id.dataValues.photo_id)
}


module.exports = {
    profilePhoto,
    findPhoto,
    deletePhoto,
}
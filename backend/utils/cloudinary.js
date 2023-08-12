const cloudinary = require('cloudinary').v2;
require('dotenv').config();

cloudinary.config({
    cloud_name: process.env.CLOUDINARY_CLOUD_NAME,
    api_key: process.env.CLOUDINARY_API_KEY,
    api_secret: process.env.CLOUDINARY_API_SECRET,
});

const uploadCloudinary = async (filepath) => {
    return new Promise((resolve) => {
        cloudinary.uploader.upload(filepath, (error, result) => {
            if (error) {
                console.error(error);
            }
            else {
                console.log(result);
                resolve(result);
            }
        });
    })
}

const deleteCloudinary = async (publicId) => {
    cloudinary.uploader.destroy(publicId, (error, result) => {
        if (error) {
            console.error(error);
        } else {
            console.log(result);
        }
    });
}

module.exports = {
    cloudinary,
    uploadCloudinary,
    deleteCloudinary,
}

const router = require("express").Router();
const upload = require("../utils/multer");
const cloudinary = require("../utils/cloudinary");
const profileImageController = require("../controllers/profileImageController.js");
const tokenManager = require('../utils/token');

router.post("/", tokenManager.verifyToken, upload.single("image"), async (request, response) => {
    const file = request.file
    const email = request.userInfo
    if (file) {
        try {
            profileImageController.deletePhoto(email);
            const result = await cloudinary.uploadCloudinary(file.path);
            await profileImageController.profilePhoto(result.secure_url, email, result.public_id);
            response.status(200).json({ url: result.secure_url });
        }
        catch (error) {
            console.error(error);
        }
    }
    else {
        response.json(false);
    }
});

module.exports = router;
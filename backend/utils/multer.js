const multer = require('multer');
const type = require("../utils/checkType");

const storageEngine = multer.diskStorage({
    destination: "./backend/images",
    filename: function (req, file, cb) {
        cb(null, `${Date.now()}-${file.originalname}`)
    }
})

const uploadMulter = multer({
    storage: storageEngine, //multer.diskStorage({}),
    limits: {
        fileSize: 2048 * 2048
    },
    fileFilter: (req, file, cb) => {
        type(file, cb);
    },
})

module.exports = uploadMulter;
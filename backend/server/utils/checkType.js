const path = require("path");

const checkFileType = function (file, cb) {

    const array_of_allowed_files = ['png', 'jpeg', 'jpg', 'gif'];
    const array_of_allowed_file_types = ['image/png', 'image/jpeg', 'image/jpg', 'image/gif'];

    //расширения
    const file_extension = file.originalname.slice(
        ((file.originalname.lastIndexOf('.') - 1) >>> 0) + 2
    );

    //тип файла
    if (array_of_allowed_files.includes(file_extension) || array_of_allowed_file_types.includes(file.memetype)) {
        return cb(null, true);
    }
    else {
        cb("Invalid file!");
    }
};

module.exports = checkFileType;
const jwt = require("jsonwebtoken");
require('dotenv').config();

function verifyToken(request, response, next) {
    const token = tokenExist(request)
    if (!token) {
        return response.sendStatus(401);
    }

    try {
        const decoded = jwt.verify(token, process.env.JWT_SECRET_KEY);
        console.log(decoded.email);
        request.userInfo = decoded.email
        next()
    } catch (error) {
        console.log(error);
        return response.status(401).send("Invalid Token");
    }

    /*jwt.verify(token, process.env.JWT_SECRET_KEY, (error) => {

        if (error) {
            console.log(error);
            return response.sendStatus(403);
        }

        next()
    })*/

}

const generateToken = (data) => {
    const token = jwt.sign(data, process.env.JWT_SECRET_KEY);
    return token
}

const tokenExist = (request) => {
    const token = request.body.token || request.query.token || request.headers[process.env.TOKEN_HEADER_KEY];
    if (!token) {
        return false
    }
    return token
}

module.exports =
{
    verifyToken,
    generateToken,
    tokenExist,
}
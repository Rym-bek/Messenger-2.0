process.env.TZ = "Europe/London";
//express
const express = require("express");
const app = express();

//server
const http = require("http");
const server = http.createServer(app);

//sequelize
const { connect } = require("./utils/sequelize");
connect();

//routers
const loginRouter = require("./routes/login");
const registerRouter = require('./routes/register');
const profileImageRouter = require('./routes/profileImage');
const userRouter = require("./routes/user");
const messagesRouter = require("./routes/messages");
const usersRouter = require("./routes/users");
const locationsRouter = require("./routes/locations");

//json
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

//dotenv
const dotenv = require("dotenv");
dotenv.config

const webSocket = require('./websockets/webSocket');

webSocket.initWebSocketServer(server);

const PORT = process.env.PORT || 5000;

server.listen(PORT, (error) => {
  if (error) {
    return console.log(`Ошибка: ${error}`);
  }
  console.log(`Сервер прослушивается на порту ${PORT}`)
});

app.use("/login", loginRouter)

app.use("/register", registerRouter)

app.use('/images/profileImage', profileImageRouter)

app.use('/user', userRouter)

app.use('/messages', messagesRouter)

app.use('/users', usersRouter)

app.use('/locations', locationsRouter)








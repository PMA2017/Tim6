"use strict";

var fs = require("fs");
var helmet = require('helmet');
var log4js = require('log4js');



var path = require('path');
global.appRoot = path.resolve(__dirname);


log4js.loadAppender('file');
//log4js.addAppender(log4js.appenders.console()); 
log4js.addAppender(log4js.appenders.file('logs/PMA-server.log'), 'upp');
 
global.logger = log4js.getLogger('upp');
logger.setLevel('INFO');

//csrf zastita testiranje
var express = require("express");
var	app = express();
var bodyParser = require("body-parser");
var cors = require("cors");
var csrf = require('csurf');


// Load keys for establishing secure HTTPS connection

var https = require("https");
var path = require("path");
var httpsOptions = {
    key: fs.readFileSync(path.resolve(__dirname, "./cert/key.pem")),
    cert: fs.readFileSync(path.resolve(__dirname, "./cert/cert.pem")),
    passphrase: "insuranceapp"
};
               
//A5 misconfig
		app.use(helmet());
        // Prevent opening page in frame or iframe to protect from clickjacking
        app.disable("x-powered-by");

        // Prevent opening page in frame or iframe to protect from clickjacking
        app.use(helmet.frameguard());

        // Prevents browser from caching and storing page
        app.use(helmet.noCache());

        // Allow loading resources only from white-listed domains
        //app.use(helmet.csp());

        // Allow communication only on HTTPS
        app.use(helmet.hsts());

        // Forces browser to only use the Content-Type set in the response header instead of sniffing or guessing it
        app.use(helmet.noSniff());


// Start secure HTTPS server

//var db = mongoose(); da li je ovo neophodno?

app.use(bodyParser.urlencoded({extended:false}));
app.use(bodyParser.json());

app.use(cors());

app.use(express.static(__dirname + '/public'));

var morgan = require('morgan');
var accessLogStream = fs.createWriteStream(path.join(__dirname, 'logs/pma-server-reqres.log'), {flags: 'a'})
//HOW TO USE CSURF?
//app.use(function(req, res, next) {
 // res.locals._csrf = req.csrfToken();
 // next();
//});
app.use(morgan("dev"));
app.use(morgan(":date :remote-addr :remote-user :method :url HTTP/:http-version :status :res[content-length] - :response-time ms",{stream: accessLogStream}));

//ovako ucitavas rute...

var Sequelize = require('sequelize');
const sequelize = new Sequelize('pmadatabase', 'pmadatabase', 'Zn0i?efat?8Q', {
  host: 'mssql2.gear.host',
  dialect: 'mssql',

  pool: {
    max: 5,
    min: 0,
    idle: 10000
  }

});

sequelize
  .authenticate()
  .then(() => {
    console.log('Connection has been established successfully.');
  })
  .catch(err => {
    console.error('Unable to connect to the database:', err);
  });

global.sequelize = sequelize;



require('./routes/generic.server.routes')(app);
require('./models/user.server.model');
require('./models/country.server.model');
require('./models/town.server.model');
require('./models/airport.server.model');
require('./models/airline.server.model');
require('./models/airplane.server.model');
require('./models/flight.server.model');
require('./models/drive.server.model');
require('./models/ticket.server.model');
require('./models/drive_ticket_connection.server.model');
require('./models/context.server.model');
require('./controllers/user.server.controller');


app.listen(3000, function() {
   logger.info("Express http server listening on port " + "3000");
});


/**
 * Created by Nemanja on 5/21/2017.
 */

var user = require(appRoot+'/models/user.server.model');
var User = user.getUser();

var country = require(appRoot+'/models/country.server.model');
var Country = country.getCountry();

var town = require(appRoot+'/models/town.server.model');
var Town = town.getTown();

var airport = require(appRoot+'/models/airport.server.model');
var Airport = airport.getAirport();

var airline = require(appRoot+'/models/airline.server.model');
var Airline = airline.getAirline();

var airplane = require(appRoot+'/models/airplane.server.model');
var Airplane = airplane.getAirplane();

var flight = require(appRoot+'/models/flight.server.model');
var Flight = flight.getFlight();

var drive = require(appRoot+'/models/drive.server.model');
var Drive = drive.getDrive();

var ticket = require(appRoot+'/models/ticket.server.model');
var Ticket = ticket.getTicket();

var drive_ticket_connection = require(appRoot+'/models/drive_ticket_connection.server.model');
var Drive_Ticket_Connection = drive_ticket_connection.getDrive_Ticket_Connection();



User.sync({force: false}).then(() => {

});

Country.sync({force: false}).then(() => {

});

Town.sync({force: false}).then(() => {

});

Airport.sync({force: false}).then(() => {

});

Airline.sync({force: false}).then(() => {

});

Airplane.sync({force: false}).then(() => {

});

Flight.sync({force: false}).then(() => {

});

Drive.sync({force: false}).then(() => {

});

Ticket.sync({force: false}).then(() => {

});

Drive_Ticket_Connection.sync({force: false}).then(() => {

});


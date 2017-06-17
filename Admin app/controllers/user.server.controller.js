"use strict"

var errorHandler = require(appRoot+'/controllers/errors.server.controller'),
user = require(appRoot+'/models/user.server.model'),
User = user.getUser(),
Sequelize = require('sequelize');

module.exports.postLoginData = postLoginData;
module.exports.getFlights = getFlights;


function postLoginData(req,res,next){

      var userId = req.body.Username;
	sequelize.model("User").findOne({ where: {Username: userId} })
		 .then(data => {

		 	if(req.body.Password == data.Password)
		    res.status(200).send(data);
			else res.status(400).send({message: "Wrong password"});
		  }).catch(error => {
		  	res.status(400).send({message:"User not found"});
	  })
}

function getFlights(req,res,next){
	var responseSent = false;
	var userId = req.params.userId;
	sequelize.model("Ticket").findAll({where: {User_ID: userId}})
		.then(tickets=>{
			//this returns tickets
			if(tickets.length>0)
			getTicketDriveConnections(tickets, function(connections){
				getDrives(connections, function(drives){
					var airlineIndex = 0;
					var timeIndex =0;
					var townFromIndex = 0;
					var townToIndex = 0;
					for(var i=0; i<drives.length; i++)
					{
						
						sequelize.model('Flight').findById(drives[i].Flight_ID).then(flight=>{
							drives[timeIndex].dataValues.endTime = new Date(drives[timeIndex].StartTime);
							drives[timeIndex].dataValues.endTime.setMinutes(drives[timeIndex].dataValues.endTime.getMinutes()+flight.FlightDuration);
							timeIndex++;
							sequelize.model('Airport').findById(flight.AirportFrom_ID).then(airportFrom=>{
								sequelize.model('Town').findById(airportFrom.Town_ID).then(town=>{

									drives[townFromIndex].dataValues.townFrom = town.Name;
									drives[townFromIndex].dataValues.townFromMark = town.Mark;
									drives[townFromIndex].dataValues.townFromLatitude = town.Latitude;
									drives[townFromIndex].dataValues.townFromLongitude = town.Longitude;
									townFromIndex++;
									sequelize.model('Airport').findById(flight.AirportTo_ID).then(airportTo=>{
										sequelize.model('Town').findById(airportTo.Town_ID).then(town=>{
											drives[townToIndex].dataValues.townToMark = town.Mark;
											drives[townToIndex].dataValues.townTo = town.Name;
											drives[townToIndex].dataValues.townToLatitude = town.Latitude;
											drives[townToIndex].dataValues.townToLongitude = town.Longitude;
											townToIndex++;
											if(airlineIndex==drives.length && !responseSent && townToIndex==drives.length)
											{
												res.status(200).send(drives);
												responseSent = true;
												return;
											}
										})
									})
								})
							})

						})

						sequelize.model('Airplane').findById(drives[i].Airplane_ID).then(airplane=>{
							sequelize.model('Airline').findById(airplane.Airline_ID).then(airline=>{
								drives[airlineIndex].dataValues.company = airline.Name;
								airlineIndex++;
								if(airlineIndex==drives.length && !responseSent && townToIndex==drives.length)
								{
									res.status(200).send(drives);
									responseSent = true;
									return;
								}
							})
						})
					}

				})
			});
			else res.status(200).send([]);
			
		}).catch(error=>{
			res.status(400).send({message:error});
		})
}

function getTicketDriveConnections(tickets,result){
	var allConnections = [];
	var realCounter = 0;
	for(var i=0; i<tickets.length; i++)
	{		
			getConnectionsForTicket(tickets[i], function(connections){

				allConnections = allConnections.concat(connections);
				realCounter++;
				if(realCounter==tickets.length)
				{
					result(allConnections);
				}
			})
	}
}

function getConnectionsForTicket(ticket, result){
		sequelize.model("Drive_Ticket_Connection").findAll({where:{Ticket_ID:ticket.id}})
			.then(connections=>{
					result(connections);
			});
}

function getDrives(connections,result){
	var allFlights = [];
	var realCounter = 0;
	
	for(var k=0; k<connections.length; k++){
					sequelize.model("Drive").findAll({where:{id:connections[k].Drive_ID}}).then(data=>{
						allFlights = allFlights.concat(data);
						realCounter++;
						if(realCounter==connections.length)
						{
							result(allFlights);
						}
				})
	}
}

function addNewUser(){
var User = user.getUser();
User.sync({force: false}).then(() => {
  // Table created
  return User.create({
    firstName: 'John',
    lastName: 'Hancock'
  });
});
}


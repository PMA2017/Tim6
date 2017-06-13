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
	var userId = req.params.userId;
	sequelize.model("Ticket").findAll({where: {User_ID: userId}})
		.then(tickets=>{
			//this returns tickets
			if(tickets.length>0)
			getTicketDriveConnections(tickets, function(connections){
				getDrives(connections, function(Drives){
					res.status(200).send(Drives);
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
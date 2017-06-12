"use strict"

var errorHandler = require(appRoot+'/controllers/errors.server.controller'),
airline = require(appRoot+'/models/airline.server.model'),
Airline = airline.getAirline(),
Sequelize = require('sequelize');

module.exports.getAirline = getAirline;


function getAirline(req,res,next){

      var userId = req.body.Username;
	sequelize.model("Airline").findAll()
		 .then(airlineData => {
		 	if(airlineData.length>0){
		 	sequelize.model("Town").findOne({where: {id:airlineData[0].Town_ID} }).then(data=>{
		 		airlineData[0].dataValues.Town = data.Name;
		 		res.status(200).send(airlineData[0]);
		 	}).catch(error=>{
		 		res.status(400).send({message:error});
		 	})}
		 	else res.status(404).send({message:"Airline not found"});
	  }).catch(error=>{
		 		res.status(400).send({message:error});
		})
}
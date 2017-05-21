"use strict"

var errorHandler = require(appRoot+'/controllers/errors.server.controller'),
user = require(appRoot+'/models/user.server.model'),
User = user.getUser(),
Sequelize = require('sequelize');

module.exports.list = list;

function list(req, res, next, tableName){
	sequelize.query("SELECT * FROM "+tableName, { type: sequelize.QueryTypes.SELECT})
		 .then(users => {
			console.log(users);
		    res.status(200).send(users);
		  }, error => {
		  	res.status(400).send({message:"SQL error"});
		  })

}
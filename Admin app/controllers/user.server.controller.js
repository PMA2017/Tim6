"use strict"

var errorHandler = require(appRoot+'/controllers/errors.server.controller'),
user = require(appRoot+'/models/user.server.model'),
User = user.getUser(),
Sequelize = require('sequelize');

module.exports.postLoginData = postLoginData;


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
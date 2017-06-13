var errorHandler = require(appRoot+'/controllers/errors.server.controller'),
Sequelize = require('sequelize');

module.exports.findReservations = findReservations;


function findReservations(req,res,next){

    /*var userId = req.body.Username;
	sequelize.model("User").findOne({ where: {Username: userId} })
		 .then(data => {

		 	if(req.body.Password == data.Password)
		    res.status(200).send(data);
			else res.status(400).send({message: "Wrong password"});
		  }).catch(error => {
		  	res.status(400).send({message:"User not found"});
	  })*/
	  
	  var transfer = 2;
	  var waiting = 8;
	  
	  var townFrom = "Bel";
	  var townTo = "Rom";
	  var passingers = 3;
	  
	  
	  
	  console.log(req.body);
	  console.log(req.body.townFrom);
	  
	  res.status(200).send({});
}
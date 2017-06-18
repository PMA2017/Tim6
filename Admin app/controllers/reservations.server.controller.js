var errorHandler = require(appRoot+'/controllers/errors.server.controller'),
Sequelize = require('sequelize');

module.exports.findReservations = findReservations;
module.exports.reserveTicket = reserveTicket;


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


//ja ti posaljem listu drive, username, broj putnika i ti upisujes u tabele ticket i drive_ticket
//i jos jedna stvar, moguce je da ti posaljem oznaku koja ne postoji, pa u tom slucaju vratis 400

function reserveTicket(req,res,next){
	var driveIds = JSON.parse("[" + req.body.drives + "]")
	var username = req.body.username;
	var passengers = req.body.passengers;
	for(var i=0; i<driveIds.length; i++)
	{
		console.log(driveIds[0]);
	}
	console.log(passengers);
	var userId;
	var ticketPrice = 0;
	sequelize.model('User').findOne({where:{Username:username}}).then(user=>{
		userId = user.id;
	
			sequelize.model('Drive').findAll({where:{id:{$or:driveIds}}}).then(drives=>{
				for(var i=0; i<drives.length; i++)
				ticketPrice+=drives[i].Price;

				ticketPrice*=passengers;
				var ticketBody = {
					User_ID: userId,
					Price: ticketPrice,
					NumberOfPassengers: passengers
				}

				var newTicket = sequelize.model('Ticket').build(ticketBody);
				newTicket.save().then(newInstance=>{
					var realIndex = 0;
					for(var i=0; i<drives.length; i++)
					{
						var conBody = {
							Drive_ID: drives[i].id,
							Ticket_ID: newInstance.id
						}
						var newConnection = sequelize.model('Drive_Ticket_Connection').build(conBody);
						newConnection.save().then(newConnection=>{
							realIndex++;
							if(realIndex==drives.length)
							{
								res.status(200).send({message:"Reservation successful"});
							}
						}).catch(error=>{
							res.status(400).send({message:error});
						})
					}
					
				}).catch(error=>{
					res.status(400).send({message:error});
				})




			})

		
	})



	
	var newTicket = sequelize.model("Ticket").build(req.body);

}
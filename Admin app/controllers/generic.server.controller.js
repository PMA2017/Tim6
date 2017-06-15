"use strict"

var errorHandler = require(appRoot+'/controllers/errors.server.controller'),
user = require(appRoot+'/models/user.server.model'),
User = user.getUser(),
Sequelize = require('sequelize');

module.exports.list = list;
module.exports.getById = getById;
module.exports.postToTable = postToTable;
module.exports.putToTable = putToTable;
module.exports.delete = deleteFromTable;
module.exports.getDrivesAroundDate = getDrivesAroundDate;

function list(req, res, next, tableName){
	sequelize.model(tableName).findAll()
		 .then(data => {
		    res.status(200).send(data);
		  }).catch(error => {
		  	res.status(400).send({message:"SQL error"});
		  })

}

function getById(req, res, next){
  var tableNameId = req.params.tableNameId,
       rowId = req.params.rowId;

sequelize.model(tableNameId).findById(rowId)
		 .then(data => {
		    res.status(200).send(data);
		  }).catch(error => {
		  	res.status(400).send({message:"SQL error"});
	  })

}

function postToTable(req,res,next){
		var instance = sequelize.model(req.params.tableToPost).build(req.body);
		instance.save().then(anotherInstance => {
	    res.status(200).send(anotherInstance);
	  })
	  .catch(error => {
	    res.status(400).send({message:error});
	})
}

function putToTable(req,res,next){
	var tableNameId = req.params.tableToPut;
	var rowId = req.body.id;
	sequelize.model(tableNameId).findById(rowId)
	  .then(data=>{
	  	if(data){
	  		data.update(req.body).then(updatedInstance=>{
	  			res.status(200).send(updatedInstance);
	  		})
	  		.catch(error => {
	  			res.status(400).send({message:error});
	  		})
	  	}
	  }).catch(error=>{
	  	res.status(400).send({message:error});
	  })
}

function deleteFromTable(req,res,next){
	var rowId = req.params.deleteRowId;
	var tableNameId = req.params.tableToDeleteFrom;

	sequelize.model(tableNameId).findById(rowId)
		.then(data=>{
						data.destroy({force:true}).then(()=>{
						res.status(200).send({message:"Instance successfully deleted"});
					}).catch(error=>{
						res.status(400).send({message:error});
					})
				
			}).catch(error=>{
			res.status(400).send({message:error});
		})
}

//https://pastebin.com/qFfn7cCX
function getDrivesAroundDate(req,res,next){
	var cityFrom = req.body.MarkTownFrom;
	var cityTo = req.body.MarkTownTo;
	var dateFrom = new Date(req.body.DateFrom);
	var dateTo = new Date(req.body.DateTo);
	var passengers = req.body.Passengers;
	var cityFromId;
	var cityToId;
	var airportsFrom;
	var airportsTo;
	sequelize.model('Town').findOne({where:{Mark:cityFrom}}).then(townFrom=>{
		cityFromId = townFrom.id;
		sequelize.model('Town').findOne({where:{Mark:cityTo}}).then(townTo=>{
			cityToId = townTo.id;
			sequelize.model('Airport').findAll({where:{Town_ID:cityFromId}}).then(airportsFrom=>{
					var queryFrom = [];
					for(var i=0; i<airportsFrom.length; i++)
					{
						queryFrom.push({AirportFrom_ID:airportsFrom[i].id});
					}
					sequelize.model('Airport').findAll({where:{Town_ID:cityToId}}).then(airportsTo=>{
						var queryTo = [];
						for(var i=0; i<airportsTo.length; i++){
							queryTo.push({AirportTo_ID:airportsTo[i].id});
						}
						sequelize.model('Flight').findAll({where:Sequelize.and(Sequelize.or(queryTo),Sequelize.or(queryFrom))}).then(flights=>{
							
							var queryFlights = [];
							for(var i=0; i<flights.length; i++){
								queryFlights.push({Flight_ID:flights[i].id});
							}
							var queryDate = [];
							var dateFromMin = new Date(dateFrom);
							dateFromMin.setDate(dateFromMin.getDate()-2);
							dateFromMin.setHours(0);
							dateFromMin.setMinutes(0);
							dateFromMin.setSeconds(0);
							var dateFromMax = new Date(dateFrom);
							dateFromMax.setDate(dateFromMax.getDate()+3);
							dateFromMax.setHours(0);
							dateFromMax.setMinutes(0);
							dateFromMax.setSeconds(0);
							var dateToMin = new Date(dateTo);
							dateToMin.setDate(dateToMin.getDate()-2);
							dateToMin.setHours(0);
							dateToMin.setMinutes(0);
							dateToMin.setSeconds(0);
							var dateToMax = new Date(dateTo);
							dateToMax.setDate(dateToMax.getDate()+3);
							dateToMax.setHours(0);
							dateToMax.setMinutes(0);
							dateToMax.setSeconds(0);

							var testDate = new Date(2017,6,15);

							console.log(dateFromMin);
							console.log(dateFromMax);
							console.log(dateToMin);
							console.log(dateToMax);


							sequelize.model('Drive').findAll({where:Sequelize.and(Sequelize.or(queryFlights),Sequelize.or({StartTime:{$gte:dateFromMin, $lte:dateFromMax}},{StartTime:{$gte:dateToMin, $lte:dateToMax}}))}).then(drives=>{
								res.json(drives);
								return;
								res.json(drives);
							})
						})
					})
					
			})
		})
	})
}



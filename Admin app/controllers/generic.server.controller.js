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
//https://pastebin.com/jq5eqJbg
//jako bi mi odgovaralo kad bi mi starttime poslao posebno datum a posebno vreme
function getDrivesAroundDate(req,res,next){
	
	
	var cityFrom = req.body.MarkTownFrom;
	var cityTo = req.body.MarkTownTo;
	var dateFrom = new Date(req.body.DateFrom);
	var passengers = req.body.Passengers;
	

	/*
	var date1string = "6/15/2017";
	var date2string ="6/21/2017";
	
	var cityFrom = "Bel";
	var cityTo = "Ber";
	var passengers = "3";
	var dateFrom = new Date(date1string);
	*/
	var dateTo;
	var endDateDefined = true;
	if(req.body.DateTo)
	dateTo = new Date(req.body.DateTo);
	else{
		dateTo = new Date("1/2/1970");
		endDateDefined = false;
	}

	console.log()

	var cityFromId;
	var cityToId;
	var airportsFrom;
	var airportsTo;
	var cityToTimeZone;
	var cityFromTimeZone;
	sequelize.model('Town').findOne({where:{Mark:cityFrom}}).then(townFrom=>{
		cityFromId = townFrom.id;
		cityFromTimeZone = townFrom.TimeZone;
		sequelize.model('Town').findOne({where:{Mark:cityTo}}).then(townTo=>{
			cityToId = townTo.id;
			cityToTimeZone = townTo.TimeZone;
			sequelize.model('Airport').findAll({where:{Town_ID:cityFromId}}).then(airportsFrom=>{
					var queryFrom = [];
					for(var i=0; i<airportsFrom.length; i++)
					{
						queryFrom.push(airportsFrom[i].id);
					}
					sequelize.model('Airport').findAll({where:{Town_ID:cityToId}}).then(airportsTo=>{
						var queryTo = [];
						for(var i=0; i<airportsTo.length; i++){
							queryTo.push(airportsTo[i].id);
						}

						sequelize.model('Flight').findAll({where:Sequelize.and({AirportTo_ID:{$or:queryTo}},{AirportFrom_ID:{$or:queryFrom}})}).then(flights=>{
							
							var queryFlights = [];
							for(var i=0; i<flights.length; i++){
								queryFlights.push(flights[i].id);
							}
							var queryDate = [];
							var dateFromMin = new Date(dateFrom);
							dateFromMin.setDate(dateFromMin.getDate()-2);
							dateFromMin.setUTCHours(0,0,0,0);
							var dateFromMax = new Date(dateFrom);
							dateFromMax.setDate(dateFromMax.getDate()+3);
							dateFromMax.setUTCHours(23,59,59,0);
							var dateToMin = new Date(dateTo);
							dateToMin.setDate(dateToMin.getDate()-2);
							dateToMin.setUTCHours(0,0,0,0);
							var dateToMax = new Date(dateTo);
							dateToMax.setDate(dateToMax.getDate()+3);
							dateToMax.setUTCHours(23,59,59,0);

							var testDate = new Date(2017,6,15);
							
							if(flights.length==0)
							{
								res.json(returnAllEmpty(endDateDefined));
								return;
									
								
							}

							sequelize.model('Drive').findAll({where:Sequelize.and({Flight_ID:{$or:queryFlights}},Sequelize.or({StartTime:{$gte:dateFromMin, $lte:dateFromMax}},{StartTime:{$gte:dateToMin, $lte:dateToMax}}))}).then(drives=>{
								var companyCount = 0;
								var freeCount = 0;
								var durationCount = 0;
								
								if(drives.length>0)
								for(var i=0; i<drives.length; i++){
									findDuration(drives,i,function(response){
										
										durationCount++;
										var hours = Math.floor(response.duration/60);
										var minutes = response.duration%60;
										drives[response.index].dataValues.duration = hours.toString()+"h "+minutes.toString()+"m";
										drives[response.index].dataValues.endTime = new Date(drives[response.index].StartTime);
										drives[response.index].dataValues.endTime.setMinutes(drives[response.index].dataValues.endTime.getMinutes()+response.duration);
										drives[response.index].dataValues.endTime.setHours(drives[response.index].dataValues.endTime.getHours()+cityToTimeZone-cityFromTimeZone);
										if(companyCount==drives.length && freeCount==drives.length && durationCount==drives.length)
										{
											organizeDrives(drives,dateFromMin,dateToMin, function(response){
												res.json(response);
												return;
											});
										}
									})
									checkIfFree(drives,i,passengers,function(response){
										
										freeCount++;
										drives[response.index].dataValues.free = response.free;
										if(companyCount==drives.length && freeCount==drives.length && durationCount==drives.length)
										{
											organizeDrives(drives,dateFromMin,dateToMin, function(response){
												res.json(response);
												return;
											});
											
										}
									})
									findCompany(drives,i,function(response){
										
										companyCount++;
										drives[response.index].dataValues.company = response.company;
										if(companyCount==drives.length && freeCount==drives.length && durationCount==drives.length)
										{
											organizeDrives(drives,dateFromMin,dateToMin, function(response){
												res.json(response);
												return;
											});
											
										}
									})
								}
								else res.json([]);
								
							})
						})
					})		
			})
		})
	})
}

function findDuration(drives,index,response){
	sequelize.model('Flight').findById(drives[index].Flight_ID).then(data=>{
		response({index:index,duration:data.FlightDuration});
	})
}

function checkIfFree(drives,index,passengers,response){
	sequelize.model('Airplane').findById(drives[index].Airplane_ID).then(data=>{
		var capacity = data.MaxNumPassanger;
		sequelize.model('Drive_Ticket_Connection').findAll({where:{Drive_ID:drives[index].id}}).then(data=>{

			var currentPassengers = 0;
			var realIndex = 0;

			if(data.length>0)
			for(var i=0; i<data.length; i++)
			{	
				sequelize.model('Ticket').findById(data[i].Ticket_ID).then(ticket=>{
					currentPassengers+=ticket.NumberOfPassengers;
					realIndex++;
					if(realIndex==data.length)
					{
						if(currentPassengers+passengers<capacity)
						{
							response({index:index,free:false});
						}
						else
						{
							response({index:index,free:true})
						}
					}
				})
				
			}
			else response({index:index,free:true});
			
		})
	})
}

function findCompany(drives,index,response){
	sequelize.model('Airplane').findById(drives[index].Airplane_ID).then(data=>{
		sequelize.model('Airline').findById(data.Airline_ID).then(data=>{
			response({index:index,company:data.Name});
		})
	})
}


function organizeDrives(drives,dateFromMin,dateToMin, response){
	var returnFlight = true;
	var currentDate = new Date();
	currentDate.setDate(currentDate.getDate()-1);
	var yesterday = currentDate;
	var newDrives = {};
	if(dateToMin<yesterday)
	{
		returnFlight = false;
	}

	dateFromMin.setDate(dateFromMin.getDate()+1);
	
	var day2 = new Date(dateFromMin);
	day2.setDate(day2.getDate()+1);
	var day3 = new Date(dateFromMin);
	day3.setDate(day3.getDate()+2);
	var day4 = new Date(dateFromMin);
	day4.setDate(day4.getDate()+3);
	var day5 = new Date(dateFromMin);
	day5.setDate(day5.getDate()+4);

	console.log(dateFromMin)
	
	var day6;
	var day7;
	var day8;
	var day9;
	var day10;
	
	if(returnFlight){
		dateToMin.setDate(dateToMin.getDate()+1);
		day7 = new Date(dateToMin);
		day7.setDate(day7.getDate()+1);
		
		day8 = new Date(dateToMin);
		day8.setDate(day8.getDate()+2);
		
		day9 = new Date(dateToMin);
		day9.setDate(day9.getDate()+3);
		
		day10 = new Date(dateToMin);
		day10.setDate(day10.getDate()+4);
	}
	
	newDrives.day1 = [];
	newDrives.day2 = [];
	newDrives.day3 = [];
	newDrives.day4 = [];
	newDrives.day5 = [];
	
	if(returnFlight){
		newDrives.day6 = [];
		newDrives.day7 = [];
		newDrives.day8 = [];
		newDrives.day9 = [];
		newDrives.day10 = [];
	}
	console.log(drives);

	for(var i=0; i<drives.length; i++){
		console.log(drives[i].StartTime.toDateString());
		console.log(dateFromMin.toDateString());
		console.log(day3.toDateString());

		drives[i].dataValues.StartTimeDate = drives[i].StartTime.toDateString().substring(4);
		drives[i].dataValues.StartTimeTime = drives[i].StartTime.getHours()+":"+drives[i].StartTime.getMinutes();
		drives[i].dataValues.EndTimeDate = drives[i].dataValues.endTime.toDateString().substring(4);
		drives[i].dataValues.EndTimeTime = drives[i].dataValues.endTime.getHours() + ":" + drives[i].dataValues.endTime.getMinutes();

		if(drives[i].StartTime.toDateString()===dateFromMin.toDateString())
		{
			
			newDrives.day1.push(drives[i]);
			
		}

		else if(drives[i].StartTime.toDateString()===day2.toDateString())
		{

			newDrives.day2.push(drives[i]);

		}
		
		else if(drives[i].StartTime.toDateString()===day3.toDateString())
		{
			newDrives.day3.push(drives[i]);
		}
		
		else if(drives[i].StartTime.toDateString()===day4.toDateString())
		{
			newDrives.day4.push(drives[i]);
		}
		
		else if(drives[i].StartTime.toDateString()===day5.toDateString())
		{
			newDrives.day5.push(drives[i]);
		}
		
		if(returnFlight)
		{
				if(drives[i].StartTime.toDateString()===dateToMin.toDateString())
				{
					newDrives.day6.push(drives[i]);
				}

				else if(drives[i].StartTime.toDateString()===day7.toDateString())
				{
					newDrives.day7.push(drives[i]);
				}
				
				else if(drives[i].StartTime.toDateString()===day8.toDateString())
				{
					newDrives.day8.push(drives[i]);
				}
				
				else if(drives[i].StartTime.toDateString()===day9.toDateString())
				{
					newDrives.day9.push(drives[i]);
				}
				
				else if(drives[i].StartTime.toDateString()===day10.toDateString())
				{
					newDrives.day10.push(drives[i]);
				}
		}		
			
	}

	response(newDrives);
}

function returnAllEmpty(endDateDefined){
	var resBody = {
		day1:[],
		day2:[],
		day3:[],
		day4:[],
		day5:[]
	}

	if(endDateDefined){
		response.day6 = [];
		response.day7 = [];
		response.day8 = [];
		response.day9 = [];
		response.day10 = [];
	}

	return resBody;

}
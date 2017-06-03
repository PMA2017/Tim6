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

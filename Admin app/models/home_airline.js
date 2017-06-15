/**
 * Created by Nemanja on 5/21/2017.
 */

var Sequelize = require('sequelize');
module.exports.getAirline = getAirline;

var town = require(appRoot+'/models/town.server.model');
var Town = town.getTown();

const Airline = sequelize.define('Airline', {
    Name: {
        type: Sequelize.STRING,
        allowNull: false
    },
    Address: {
    	type: Sequelize.STRING,
    	allowNull: false
    },
    PhoneNumber:{
    	type: Sequelize.STRING,
    	allowNull: false
    },
    Town_ID:{
    	type: Sequelize.INTEGER,
        allowNull: false,
        references: {
            model: Town,
            key: 'id'
   	 	}
	}

}, {
    freezeTableName: true // Model tableName will be the same as the model name
});

function getAirline(){
    return Airline;
}

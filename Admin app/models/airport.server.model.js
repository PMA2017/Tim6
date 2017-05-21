
/**
 * Created by Nemanja on 5/21/2017.
 */

var Sequelize = require('sequelize');
module.exports.getAirport = getAirport;

var town = require(appRoot+'/models/town.server.model');
var Town = town.getTown();

const Airport = sequelize.define('Airport', {
    Mark: {
        type: Sequelize.STRING,
        allowNull: false
    },
    Name: {
        type: Sequelize.STRING
    },
    Town_ID: {
        type: Sequelize.INTEGER,
        allowNull: false,
        references: {
            model: Town,
            key: 'id',
        }
    }
}, {
    freezeTableName: true // Model tableName will be the same as the model name
});

function getAirport(){
    return Airport;
}
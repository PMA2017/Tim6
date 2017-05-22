/**
 * Created by Nemanja on 5/21/2017.
 */

var Sequelize = require('sequelize');
module.exports.getDrive = getDrive;

var airplane = require(appRoot+'/models/airplane.server.model');
var Airplane = airplane.getAirplane();

var flight = require(appRoot+'/models/flight.server.model');
var Flight = flight.getFlight();

const Drive = sequelize.define('Drive', {
    StartTime: {
        type: Sequelize.DATE,
        allowNull: false
    },
    Airplane_ID: {
        type: Sequelize.INTEGER,
        allowNull: false,
        references: {
            model: Airplane,
            key: 'id',
        }
    },
    Flight_ID: {
        type: Sequelize.INTEGER,
        allowNull: false,
        references: {
            model: Flight,
            key: 'id',
        }
    }
}, {
    freezeTableName: true // Model tableName will be the same as the model name
});

function getDrive(){
    return Drive;
}
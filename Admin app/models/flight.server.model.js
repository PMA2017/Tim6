/**
 * Created by Nemanja on 5/21/2017.
 */

var Sequelize = require('sequelize');
module.exports.getFlight = getFlight;

var airport = require(appRoot+'/models/airport.server.model');
var Airport = airport.getAirport();

const Flight = sequelize.define('Flight', {
    FlightDuration: {
        type: Sequelize.INTEGER,
        allowNull: false
    },
    Price: {
        type: Sequelize.INTEGER,
        allowNull: false
    },
    AirportFrom_ID: {
        type: Sequelize.INTEGER,
        allowNull: false,
        references: {
            model: Airport,
            key: 'id',
        }
    },
    AirportTo_ID: {
        type: Sequelize.INTEGER,
        allowNull: false,
        references: {
            model: Airport,
            key: 'id',
        }
    }
}, {
    freezeTableName: true // Model tableName will be the same as the model name
});

function getFlight(){
    return Flight;
}
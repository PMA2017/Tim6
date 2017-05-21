<<<<<<< HEAD
/**
 * Created by Nemanja on 5/21/2017.
 */

var Sequelize = require('sequelize');
module.exports.getAirplane = getAirplane;

var airline = require(appRoot+'/models/airline.server.model');
var Airline = airline.getAirline();

const Airplane = sequelize.define('Airplane', {
    Mark: {
        type: Sequelize.STRING,
        allowNull: false
    },
    Name: {
        type: Sequelize.STRING
    },
    MaxNumPassanger: {
        type: Sequelize.INTEGER,
        allowNull: false
    },
    Airline_ID: {
        type: Sequelize.INTEGER,
        allowNull: false,
        references: {
            model: Airline,
            key: 'id',
        }
    }
}, {
    freezeTableName: true // Model tableName will be the same as the model name
});

function getAirplane(){
    return Airplane;
}

=======
/**
 * Created by Nemanja on 5/21/2017.
 */

var Sequelize = require('sequelize');
module.exports.getAirplane = getAirplane;

var airline = require(appRoot+'/models/airline.server.model');
var Airline = airline.getAirline();

const Airplane = sequelize.define('Airplane', {
    Mark: {
        type: Sequelize.STRING,
        allowNull: false
    },
    Name: {
        type: Sequelize.STRING
    },
    MaxNumPassanger: {
        type: Sequelize.INTEGER,
        allowNull: false
    },
    Airline_ID: {
        type: Sequelize.INTEGER,
        allowNull: false,
        references: {
            model: Airline,
            key: 'id',
        }
    }
}, {
    freezeTableName: true // Model tableName will be the same as the model name
});

function getAirplane(){
    return Airplane;
}

>>>>>>> 421ad6a34e5dc4f7b4af00d363faa6e56d54b8bb

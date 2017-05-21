<<<<<<< HEAD
/**
 * Created by Nemanja on 5/21/2017.
 */

var Sequelize = require('sequelize');
module.exports.getAirline = getAirline;

const Airline = sequelize.define('Airline', {
    Name: {
        type: Sequelize.STRING,
        allowNull: false
    }
}, {
    freezeTableName: true // Model tableName will be the same as the model name
});

function getAirline(){
    return Airline;
}
=======
/**
 * Created by Nemanja on 5/21/2017.
 */

var Sequelize = require('sequelize');
module.exports.getAirline = getAirline;

const Airline = sequelize.define('Airline', {
    Name: {
        type: Sequelize.STRING,
        allowNull: false
    }
}, {
    freezeTableName: true // Model tableName will be the same as the model name
});

function getAirline(){
    return Airline;
}
>>>>>>> 421ad6a34e5dc4f7b4af00d363faa6e56d54b8bb

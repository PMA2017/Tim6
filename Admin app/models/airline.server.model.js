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

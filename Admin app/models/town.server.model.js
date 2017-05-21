/**
 * Created by Nemanja on 5/21/2017.
 */

var Sequelize = require('sequelize');
module.exports.getTown = getTown;

var country = require(appRoot+'/models/country.server.model');
var Country = country.getCountry();

const Town = sequelize.define('Town', {
    Name: {
        type: Sequelize.STRING,
        allowNull: false
    },
    ZipCode: {
        type: Sequelize.INTEGER, unique: true,
        allowNull: false
    },
    Longitude: {
        type: Sequelize.INTEGER,
        allowNull: false,
        validate: { min: -180, max: 180 }
    },
    Latitude: {
        type: Sequelize.INTEGER,
        allowNull: false,
        validate: { min: -180, max: 180 }
    },
    TimeZone: {
        type: Sequelize.INTEGER,
        allowNull: false
    },
    Country_ID: {
        type: Sequelize.INTEGER,
        allowNull: false,
        references: {
            model: Country,
            key: 'id',
        }
    }
}, {
    freezeTableName: true // Model tableName will be the same as the model name
});

function getTown(){
    return Town;
}

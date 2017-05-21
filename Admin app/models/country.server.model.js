/**
 * Created by Nemanja on 5/21/2017.
 */

var Sequelize = require('sequelize');
module.exports.getCountry = getCountry;

const Country = sequelize.define('Country', {
    Mark: {
        type: Sequelize.STRING
    },
    Name: {
        type: Sequelize.STRING
    }
});

function getCountry(){
    return Country;
}

Country.sync({force: false}).then(() => {
    return Country.create({
        mark: "Srb",
        name: 'Serbia'
    });
});
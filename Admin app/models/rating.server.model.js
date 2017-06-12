/**
 * Created by Milos on 6/11/2017.
 */

var Sequelize = require('sequelize');
module.exports.getRating = getRating;

//import other table
//var User = require(appRoot+'/models/user.server.model');
//var User = user.getUser();
//
var User = sequelize.model('User');
var Drive = sequelize.model('Drive');

const Rating = sequelize.define('Rating', {
    Rating: {
        type: Sequelize.INTEGER,
        allowNull: false,
        validate: { min: 1, max: 5 }
    },

    User_ID:{
    	type: Sequelize.INTEGER,
        allowNull: false,
        references: {
            model: User,
            key: 'id'
        }
    },

    Drive_ID:{
        type: Sequelize.INTEGER,
        allowNull: false,
        references: {
            model: Drive,
            key: 'id'
        }
    }

}, {
    freezeTableName: true // Model tableName will be the same as the model name
});

function getRating(){
    return Rating;
}

/**
 * Created by Milos on 6/11/2017.
 */

var Sequelize = require('sequelize');
module.exports.getComment = getComment;

//import other table
//var User = require(appRoot+'/models/user.server.model');
//var User = user.getUser();
//
var User = sequelize.model('User');
var Drive = sequelize.model('Drive');

const Comment = sequelize.define('Comment', {
    Content: {
        type: Sequelize.STRING,
        allowNull: false
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

function getComment(){
    return Comment;
}

/**
 * Created by Nemanja on 5/21/2017.
 */

var Sequelize = require('sequelize');
module.exports.getTicket = getTicket;

var user = require(appRoot+'/models/user.server.model');
var User = user.getUser();

const Ticket = sequelize.define('Ticket', {
    Price: {
        type: Sequelize.INTEGER,
        allowNull: false
    },
    User_ID: {
        type: Sequelize.INTEGER,
        allowNull: false,
        references: {
            model: User,
            key: 'id',
        }
    }
}, {
    freezeTableName: true // Model tableName will be the same as the model name
});

function getTicket(){
    return Ticket;
}


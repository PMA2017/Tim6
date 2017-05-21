/**
 * Created by Nemanja on 5/21/2017.
 */

var Sequelize = require('sequelize');
module.exports.getDrive_Ticket_Connection = getDrive_Ticket_Connection;

var drive = require(appRoot+'/models/drive.server.model');
var Drive = drive.getDrive();

var ticket = require(appRoot+'/models/ticket.server.model');
var Ticket = ticket.getTicket();

const Drive_Ticket_Connection = sequelize.define('Drive_Ticket_Connection', {
    Drive_ID: {
        type: Sequelize.INTEGER,
        allowNull: false,
        references: {
            model: Drive,
            key: 'id',
        }
    },
    Ticket_ID: {
        type: Sequelize.INTEGER,
        allowNull: false,
        references: {
            model: Ticket,
            key: 'id',
        }
    }
}, {
    freezeTableName: true // Model tableName will be the same as the model name
});

function getDrive_Ticket_Connection(){
    return Drive_Ticket_Connection;
}

"use strict"
var Sequelize = require('sequelize');
module.exports.getUser = getUser;

const User = sequelize.define('User', {
  FirstName: {
    type: Sequelize.STRING
  },
  LastName: {
    type: Sequelize.STRING
  }
});

function getUser(){
	return User;
}


"use strict"
var Sequelize = require('sequelize');
module.exports.getUser = getUser;

const User = sequelize.define('User', {
  Username: {
	type: Sequelize.STRING, unique: true,
	allowNull: false
  },
  FirstName: {
    type: Sequelize.STRING,
	allowNull: false
  },
  LastName: {
    type: Sequelize.STRING,
	allowNull: false
  },
  Password: {
	type: Sequelize.STRING,
	allowNull: false
  },
  Role: {
	  type: Sequelize.INTEGER,
	  allowNull: false
  }
});

function getUser(){
	return User;
}


"use strict"
var Sequelize = require('sequelize');
module.exports.getUser = getUser;


const User = sequelize.define('user', {
  firstName: {
    type: Sequelize.STRING
  },
  lastName: {
    type: Sequelize.STRING
  }
});

function getUser(){
	return User;
}




//testing country
const Country = sequelize.define('countries', {
  name: {
    type: Sequelize.STRING
  }
});

Country.sync({force: false}).then(() => {
  // Table created
  return Country.create({
    name: 'Serbia'
  });
});




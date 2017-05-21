"use strict"

var user = require(appRoot+'/models/user.server.model');

var User = user.getUser();
User.sync({force: false}).then(() => {
  // Table created
  return User.create({
    firstName: 'John',
    lastName: 'Hancock'
  });
});
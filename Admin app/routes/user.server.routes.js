var users = require(appRoot+'/controllers/user.server.controller.js');

module.exports = function(app){

app.route('/api/User/postLoginData')
    .post(users.postLoginData);

}
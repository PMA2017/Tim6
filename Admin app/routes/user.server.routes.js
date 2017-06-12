var users = require(appRoot+'/controllers/user.server.controller.js');

module.exports = function(app){

app.route('/api/User/postLoginData')
    .post(users.postLoginData);

app.route('/api/User/getFlights/:userId')
	.get(users.getFlights);
app.param('userId', users.getFlights);

}
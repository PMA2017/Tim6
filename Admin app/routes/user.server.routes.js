var users = require(appRoot+'/controllers/user.server.controller.js');

module.exports = function(app){

app.route('/api/User/postLoginData')
    .post(users.postLoginData);

app.route('/api/User/getFlights/:userId')
	.get(users.getFlights);
app.param('userId', users.getFlights);

app.route('/api/getRatingForFlight/:userRatingId/:driveId')
	.get(users.getRatingForFlight);
app.param('userRatingId',users.getRatingForFlight);
app.param('driveId',users.getRatingForFlight);

app.route('/api/getRatingsForFlights/:userAllFlights')
	.post(users.getRatingsForFlights);
app.param('userAllFlights',users.getRatingsForFlights);
}
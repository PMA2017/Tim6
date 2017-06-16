var reservations = require(appRoot+'/controllers/reservations.server.controller.js');

module.exports = function(app){

app.route('/api/findReservation')
    .post(reservations.findReservations);

app.route('/api/reserveTicket')
	.post(reservations.reserveTicket);

}
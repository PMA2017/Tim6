var airline = require(appRoot+'/controllers/airline.server.controller.js');

module.exports = function(app){

app.route('/api/getAirline')
    .get(airline.getAirline);

}
var genericOperations = require(appRoot+'/controllers/generic.server.controller.js');

module.exports = function(app){
app.route('/api/get/:tableName')
    .get(genericOperations.list)
app.param('tableName',genericOperations.list);
}
var genericOperations = require(appRoot+'/controllers/generic.server.controller.js');

module.exports = function(app){
app.route('/api/get/:tableName')
    .get(genericOperations.list)
app.param('tableName',genericOperations.list);
app.route('/api/getById/:tableNameId/:rowId')
	.get(genericOperations.getById)
app.param('tableNameId',genericOperations.getById);
app.param('rowId',genericOperations.getById);

app.route('/api/postToTable/:tableToPost')
	.post(genericOperations.postToTable);
app.param('tableToPost',genericOperations.postToTable);
}
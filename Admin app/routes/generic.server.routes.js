var genericOperations = require(appRoot+'/controllers/generic.server.controller.js');

module.exports = function(app){
app.route('/api/get/:tableName')
    .get(genericOperations.list)
app.param('tableName',genericOperations.list);
app.route('/api/get/:tableNameId/:rowId')
	.get(genericOperations.getById)
app.param('tableNameId',genericOperations.getById);
app.param('rowId',genericOperations.getById);

app.route('/api/postToTable/:tableToPost')
	.post(genericOperations.postToTable);
app.param('tableToPost',genericOperations.postToTable);

app.route('/api/generic/:tableToPut')
	.put(genericOperations.putToTable);
app.param('tableToPut',genericOperations.putToTable);

app.route('/api/generic/:tableToDeleteFrom/:deleteRowId')
	.delete(genericOperations.delete);
app.param('tableToDeleteFrom',genericOperations.delete);
app.param('deleteRowId',genericOperations.delete);

}
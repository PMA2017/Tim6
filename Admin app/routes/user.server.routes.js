var users = require(appRoot+'/controllers/user.server.controller.js');

module.exports = function(app){
app.route('/api/users')
    .get(users.list)
    .post(users.postLoginData);

app.route('/api/users/:userId')
    .get(users.getById);
app.param('userId', users.getById);

}
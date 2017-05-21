

exports.getErrorMessage = function(err){
    if(err.errors){
        for(var errName in err.errors){
            if(err.errors[errName].message)
             return err.errors[errName].message;
         	else return "Unknown server error";
        }
    } else if(err.message){
		 return err.message;

    } else {
        return 'Uknown server error';
    }
};


exports.logErrorMessage = function(errMessage){
	var fs = require('fs');
	fs.appendFile(appRoot+"/logs/error log.txt", new Date()+":     "+errMessage+"\n", function(err) {
    if(err) {
        return console.log(err);
    }
    else{
    console.log("Error saved to error log.txt");
	}
}); 
}
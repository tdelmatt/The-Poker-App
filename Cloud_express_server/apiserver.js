/*********************************************
**Name: Taylor Del Matto
**Class: CS 496
**Assignment: Final Project
**Date: 6/10/2016
***********************************************/
var express = require('express');

var AWS = require("aws-sdk");

AWS.config.update({
  accessKeyId:"",
  secretAccessKey:"",
  region: ""
});

var app = express();
var handlebars = require('express-handlebars').create({defaultLayout:'main'});
var bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(express.static('views'));

app.engine('handlebars', handlebars.engine);
app.set('view engine', 'handlebars');
app.set('port', 3000);

var dynamodb = new AWS.DynamoDB();
var docClient = new AWS.DynamoDB.DocumentClient();
function getcasino(casinoname, afunct, array, lastcallbackstatus){
	//querycasinoname
	//afunct(string, array, lastcallbackstatus)...define afunct(also consider changing for in to size based iterator so we know what element it is)
		//in afunct...if last element, after appending to array, res.send(array)
}

function getcasinoslist(username, password){
	getprofile(username, password, function(profile){
		//for each in profile.casinos
		for(var i = 0; i < profile.casinos.length; i++){
			if(profile.casinos[i] != "Red Dragon" && profile.casinos[i] != "Bellagio" && profile.casinos[i] != "MGM Grand" && profile.casinos[i] != "Morongo" && profile.casinos[i] != "Aria Resort"){
				console.log("casino not recognized");
			}
			else{
					var params = {
						TableName : "Casinos",
						KeyConditionExpression: "name = :uuuu",
						ExpressionAttributeValues:{
							":uuuu": username
						}
					};
					docClient.query(params, function(err, data){
						if(err){
							console.log("Unable to query. Error:", JSON.stringify(err, null, 2));
							//callfunct(0);
						}
						else{
							console.log("query triggered");
							if(data.Items.length == 0){
								console.log("data is null");
								callfunct(0);
								return;
							}
							var j = 0;
							data.Items.forEach(function(item){
								console.log(item);
								var gms;
								//for all in pokergames, append to string gms
								/*item.pokergames{
									gms = 
								}*/
								//for all in tournament times, specials, do same thing
								var strout = 'The ' + item.name + ' casino\n' + 'Location: ' + item.address; //+ '\n card games are ' + gms + '\n tournament times are ' + trns + '\n specials are ' + spcs;
								casarray[i] = strout;
								j++;
								//if last element in array, send array
								if(i == (profile.casinos.length-1)){
									//res.send(profile.casinos);
								}
								
							});
			
						}
					});
				
			}
			
		}
			//if that casino exists
			//create string
			//append string to array
	});
}
function casinoexists(){
	//if casino exists
}

function createcasinoinstances(){
	var params = {
				TableName: "Casinos",
				Item: {"name": "Red Dragon",
						"address": "14 E Lane, Kentucky Street Kentucky",
						"pokergames": ["Texas Holdem", "Pot Limit Omaha", "7 Card Stud"],
						"tournamenttimes": ["Monday 8pm", "Friday 10am"],
						"specials": ["High Hand $200 M-F 10am-2pm"]
				}
			};
				
				docClient.put(params, function(err, data){
					if (err) {
						console.error("Unable to add ");
				   } else {
					   console.log("PutItem succeeded:");
				   }
				});
				var params1 = {
				TableName: "Casinos",
				Item: {"name": "Morongo",
						"address": "14 W Street, Los Angeles",
						"pokergames": ["Texas Holdem", "Pot Limit Omaha", "7 Card Stud", "HORSE"],
						"tournamenttimes": ["Monday 8pm", "Saturday 10am"],
						"specials": ["High Hand $200 M-F 10am-2pm, 11pm-5am"]
				}
			};
				
				docClient.put(params1, function(err, data){
					if (err) {
						console.error("Unable to add ");
						
				   } else {
					   console.log("PutItem succeeded:");
					   
				   }
				});
		var params2 = {
				TableName: "Casinos",
				Item: {"name": "Bellagio",
						"address": "14th Ave, VEGAS STRIP",
						"pokergames": ["Texas Holdem", "Pot Limit Omaha", "7 Card Stud", "RAZZ"],
						"tournamenttimes": ["Sunday 5pm", "Tuesday 1am"],
						"specials": ["Double Payout Sat-Sun 1am-5pm"]
				}
			};
				
				docClient.put(params2, function(err, data){
					if (err) {
						console.error("Unable to add ");
				   } else {
					   console.log("PutItem succeeded:");
					   
				   }
				});
			var params3 = {
				TableName: "Casinos",
				Item: {"name": "MGM Grand",
						"address": "3rd place, VEGAS STRIP",
						"pokergames": ["Texas Holdem", "Pot Limit Omaha", "7 Card Stud", "5 Card Draw"],
						"tournamenttimes": ["Thursday 8pm", "Friday 10am"],
						"specials": ["High Hand $500 M-F 8am-8pm"]
				}
			};
				
				docClient.put(params3, function(err, data){
					if (err) {
						console.error("Unable to add ");
				   } else {
					   console.log("PutItem succeeded:");
				   }
				});
		var params4 = {
				TableName: "Casinos",
				Item: {"name": "Aria Resort",
						"address": "2nd place, VEGAS STRIP",
						"pokergames": ["Texas Holdem", "Pot Limit Omaha", "7 Card Stud"],
						"tournamenttimes": ["Monday 8pm", "Friday 10am"],
						"specials": ["High Hand $700 M-F 10am-2pm"]
				}
			};
				
				docClient.put(params4, function(err, data){
					if (err) {
						console.error("Unable to add ");
				   } else {
					   console.log("PutItem succeeded:");
				   }
				});

}
function onScancasinos(err, data){//displays stocks
	if (err) {
        console.error("Unable to scan the table. Error JSON:", JSON.stringify(err, null, 2));
    } else {
		data.Items.forEach(function(casino){
			console.log("the name is " + casino.name + "\n the Address is " + casino.address);
		});
	}
	
}

function addcasino(username, casinos){
	var params = {
    TableName:"Users",
    Key:{
        "username": username
    },
    UpdateExpression: "set casinos=:c",
    ExpressionAttributeValues:{
        ":c":casinos
    },
    ReturnValues:"UPDATED_NEW"
	};
	
	docClient.update(params, function(err, data){
		if (err) {
        console.error("Unable to update item. Error JSON:", JSON.stringify(err, null, 2));
		//res.send("error");
		} else {
        console.log("UpdateItem succeeded:", JSON.stringify(data, null, 2));
		//res.send("good");
		}
	});
}

function onScanusers(err, data){//displays stocks
	if (err) {
        console.error("Unable to scan the table. Error JSON:", JSON.stringify(err, null, 2));
    } else {
		data.Items.forEach(function(user){
			console.log("the username is " + user.username + "\n the password is " + user.password + "\n the casinos are " + 
			user.casinos + "\n the dob is " + user.dob + "\nthe playerstatus is " + user.playerstatus);
		});
	}
	
}
function getprofile(username, password, callfunc){
	var params = {
		TableName : "Users",
		KeyConditionExpression: "username = :uuuu",
		ExpressionAttributeValues:{
			":uuuu": username
		}
	};
	docClient.query(params, function(err, data){
		if(err){
			console.log("Unable to query. Error:", JSON.stringify(err, null, 2));
			console.log("in get profile, this should not display");
			return 0;
		}
		else{
			console.log("query triggered");
			data.Items.forEach(function(item){
				//if password = password
				//send information				
				if(password == item.password){
					callfunc(item);
				}
				else{
					console.log("usernames are the same, but passwords do not match??? in get profile, this should not display\n");
					return 2;
				}
			});
			
		}
	});
}
function queryprofileexists(username, password, callfunct){
	var params = {
		TableName : "Users",
		KeyConditionExpression: "username = :uuuu",
		ExpressionAttributeValues:{
			":uuuu": username
		}
	};
	docClient.query(params, function(err, data){
		if(err){
			console.log("Unable to query. Error:", JSON.stringify(err, null, 2));
			//callfunct(0);
		}
		else{
			console.log("query triggered");
			if(data.Items.length == 0){
				console.log("data is null");
				callfunct(0);
				return;
			}
			data.Items.forEach(function(item){
				console.log(item);
				//if password = password
				//send information				
				if(password == item.password){
					console.log(item.password);
					callfunct(1);
					return;
				}
				else{
					console.log("usernames are the same, but passwords do not match???\n");
					callfunct(2);
					return;
				}
			});
			
		}
	});
}

//deletes users table
app.get('/deleteusers', function(req, res, next){
	var params = {
    TableName : "Users"
	};
	var params1 = {
    TableName : "Casinos"
	};

	dynamodb.deleteTable(params, function(err, data) {
		if (err) {
			console.error("Unable to delete table. Error JSON:", JSON.stringify(err, null, 2));
		} else {
			console.log("Deleted table. Table description JSON:", JSON.stringify(data, null, 2));
		}
	});
	
	/*dynamodb.deleteTable(params1, function(err, data) {
		if (err) {
			console.error("Unable to delete table. Error JSON:", JSON.stringify(err, null, 2));
		} else {
			console.log("Deleted table. Table description JSON:", JSON.stringify(data, null, 2));
		}
	});*/
	res.send("tables deleted!");
});

app.get('/deletecasinos', function(req, res, next){

	var params = {
    TableName : "Casinos"
	};

	dynamodb.deleteTable(params, function(err, data) {
		if (err) {
			console.error("Unable to delete table. Error JSON:", JSON.stringify(err, null, 2));
		} else {
			console.log("Deleted table. Table description JSON:", JSON.stringify(data, null, 2));
		}
	});
	
	res.send("tables deleted!");
});

//creates users table
app.get('/createusers', function(req, res, next){	
	//create users table
	var params = {
		TableName : "Users",
		KeySchema: [       
			{ AttributeName: "username", KeyType: "HASH"},  //Partition key
		],
		AttributeDefinitions: [       
			{ AttributeName: "username", AttributeType: "S" }//,
			//{ AttributeName: "_value", AttributeType: "N" }
			//{ AttributeName: "10yearvalues", AttributeType: "N" },//This should be a list
			//{ AttributeName: "10yeartimes", AttributeType: "N" }//this also should be a list
		],
		ProvisionedThroughput: {       
			ReadCapacityUnits: 10, 
			WriteCapacityUnits: 10
		}
	};
	dynamodb.createTable(params, function(err, data) {
		if (err) {
			console.error("Unable to create table. Error JSON:", JSON.stringify(err, null, 2));
		} else {
			console.log("Created table. Table description JSON:", JSON.stringify(data, null, 2));
		}
	});
	res.send("tables created!");
});

//creates casino table
app.get('/createcasinos', function(req, res, next){	
	//create casinos table
	
	var params = {
		TableName : "Casinos",
		KeySchema: [       
			{ AttributeName: "name", KeyType: "HASH"},  //Partition key
		],
		AttributeDefinitions: [       
			{ AttributeName: "name", AttributeType: "S" }//,
			//{ AttributeName: "_value", AttributeType: "N" }
			//{ AttributeName: "10yearvalues", AttributeType: "N" },//This should be a list
			//{ AttributeName: "10yeartimes", AttributeType: "N" }//this also should be a list
		],
		ProvisionedThroughput: {       
			ReadCapacityUnits: 10, 
			WriteCapacityUnits: 10
		}
	};
	dynamodb.createTable(params, function(err, data) {
		if (err) {
			console.error("Unable to create table. Error JSON:", JSON.stringify(err, null, 2));
		} else {
			console.log("Created table. Table description JSON:", JSON.stringify(data, null, 2));
			createcasinoinstances();
		}
	});
	res.send("tables created!");
});
app.post('/cloudcompute', function(req, res, next){
	var postbody = {};
	postbody = req.body;
	console.log(postbody);
	
	var result = (parseInt(postbody.np) * parseInt(postbody.round) * parseInt(postbody.mc) * parseInt(postbody.pcr)) % 100;
	var results = result.toString();
	res.send(results);
});

//change this back to post after you get it to work
//this handler should take user profile parameters and use them to 
//create a user profile
app.post('/newuserprofile', function(req, res, next){
	//console.log("a post was recieved");
	var postbody = {};
	postbody = req.body;
	console.log(postbody);
	//var name = "the dude";
	//var password = "pot420420";
	queryprofileexists(postbody.username, postbody.password, function(result){
		console.log(result);
		if(result == 0){
			console.log("create new profile initiated\n");
			var casinos = [];
			var params = {
				TableName: "Users",
				Item: {"username": postbody.username,
						"password": postbody.password,
						"casinos": casinos,
						"dob": postbody.dob,
						"playerstatus": postbody.playerstatus
				}
			};
				
				docClient.put(params, function(err, data){
					if (err) {
						console.error("Unable to add ");
						res.send("error");
				   } else {
					   console.log("PutItem succeeded:");
					   res.send("success");
				   }
				});
			}
			else{
				res.send("error");
			}
	});
	
	
	
	//console.log(postbody);
	//jpost = {};
	//jpost = JSON.parse(postbody);
	//console.log(postbody.username);
	//console.log(jpost.username);
	//jpost.username = 1;
	//jpost.password = "suck me";
	//console.log(jpost);
});
//this post handler should check to see if a profile exists
//if the profile exists, it should send back the profile data to the mobile application
app.post('/login', function(req, res, next){
	var postbody = {};
	postbody = req.body;
	//use key to query 
	queryprofileexists(postbody.username, postbody.password, function(result){
		if(result == 1){
			getprofile(postbody.username, postbody.password, function(result){
				res.send(JSON.stringify(result));
			});
		}
		else{
			res.send("error");
		}
	});
});
app.get('/print', function(req,res,next){
	var params = {
		TableName: "Users"
	}
	docClient.scan(params, onScanusers);
});
app.get('/printcasinos', function(req,res,next){
	var params = {
		TableName: "Casinos"
	}
	docClient.scan(params, onScancasinos);
});
app.put('/updateprofile', function(req, res, next){	
	var postbody = {};
	postbody = req.body;
	console.log(postbody);
	var params = {
    TableName:"Users",
    Key:{
        "username": postbody.username
    },
    UpdateExpression: "set password = :p, dob=:d, playerstatus=:ps",
    ExpressionAttributeValues:{
        ":p":postbody.password,
        ":d":postbody.dob,
        ":ps":postbody.playerstatus
    },
    ReturnValues:"UPDATED_NEW"
	};
	
	docClient.update(params, function(err, data){
		if (err) {
        console.error("Unable to update item. Error JSON:", JSON.stringify(err, null, 2));
		res.send("error");
		} else {
        console.log("UpdateItem succeeded:", JSON.stringify(data, null, 2));
		res.send("good");
		}
	});
	
});

app.delete('/deleteprofile', function(req, res, next){	
	var datain = {};
	datain = req.query;
	console.log(datain.username);
		var params = {
		TableName:"Users",
		Key:{
			"username":datain.username
		}
	};

	//console.log("Attempting a conditional delete...");
	docClient.delete(params, function(err, data) {
		if (err) {
			console.error("Unable to delete item. Error JSON:", JSON.stringify(err, null, 2));
			res.send("error unable to delete");
		} else {
			console.log("DeleteItem succeeded:", JSON.stringify(data, null, 2));
			res.send("delete succeeded");
		}
	});
});



app.get('/startpage', function(req, res, next){	
	console.log("this happened");
	var result = queryprofileexists("taylor", "guest", function(result){
		console.log(result);
	});
	//var it1 = getprofile("taylor", "guest");
	//console.log(it1);
	
});

app.get('/getcasinoslist', function(req, res, next){
	//get url encoded parameters, then call function
	var datain = {};
	datain = req.query;
	console.log(datain.username);
	console.log(datain.password);
		getprofile(datain.username, datain.password, function(profile){
		//for each in profile.casinos
		var casarray = [];
		var count = 0;
		for(var i = 0; i < profile.casinos.length; i++){
			if(profile.casinos[i] != "Red Dragon" && profile.casinos[i] != "Bellagio" && profile.casinos[i] != "MGM Grand" && profile.casinos[i] != "Morongo" && profile.casinos[i] != "Aria Resort"){
				console.log("casino not recognized");
			}
			else{
					if(i == (profile.casinos.length-1)){
						var endnm = profile.casinos[i];
					}
					var params = {
						TableName : "Casinos",
						KeyConditionExpression: "#nm = :uuuu",
						ExpressionAttributeNames:{
						"#nm": "name"
						},
						ExpressionAttributeValues:{
							":uuuu": profile.casinos[i]
						}
					};
					docClient.query(params, function(err, data){
						if(err){
							console.log("Unable to query. Error:", JSON.stringify(err, null, 2));
							//callfunct(0);
						}
						else{
							count++;
							console.log("query triggered");
							if(data.Items.length == 0){
								console.log("data is null");
							}
							var j = 0;
							data.Items.forEach(function(item){
								console.log(item);
								var gms = '';
								var trns = '';
								var spcs = '';
								//for all in pokergames, append to string gms
									for(var k in item.pokergames){
										gms = gms + ' ' + item.pokergames[k]; 
									}
									for(var k in item.tournamenttimes){
										trns = trns + ' ' + item.tournamenttimes[k];
									}
									for(var k in item.specials){
										spcs = spcs + ' ' + item.specials[k];
									}
								//item.pokergames{
								//	gms = 
								//}
								//for all in tournament times, specials, do same thing
								var strout = 'The ' + item.name + ' Casino\n' + 'Location: ' + item.address + '\n Card Games are ' + gms; /*+ '\n Tournament Times are ' + trns + '\n Specials are ' + spcs;*/
								console.log(strout);
								casarray.push(strout);
								j++;
								//if last element in array, send array
								console.log("item.name is " + item.name);
								console.log("endnm " + endnm);
								console.log(casarray);
								if(count == profile.casinos.length){
									console.log(casarray);
									console.log("array sent!");
									var argh = {};
									argh.casinos = casarray;
									res.send(JSON.stringify(argh));
								}
							});
						}
					});
			}
			
		}
			//if that casino exists
			//create string
			//append string to array
	});
	
});

app.get('/addcasino', function(req, res, next){
	var datain = {};
	datain = req.query;
	console.log(datain.username);
	console.log(datain.password);
	console.log(datain.casino);
	//if casino name exists
	var params = {
		TableName : "Casinos",
		KeyConditionExpression: "#nm = :uuuu",
		ExpressionAttributeNames:{
		"#nm": "name"
		},
		ExpressionAttributeValues:{
			":uuuu": datain.casino
		}
	};
	docClient.query(params, function(err, data){
		if(err){
			console.log("Unable to query. Error:", JSON.stringify(err, null, 2));
			//callfunct(0);
			res.send("error");
		}
		else{
			console.log("query triggered");
			if(data.Items.length == 0){
				console.log("data is null");
				res.send("error");
			}
			else{
				var incasinos = 0;
				data.Items.forEach(function(item){
					//if casino name is not already in the users casino list
					getprofile(datain.username, datain.password, function(profile){
						for(var k in profile.casinos){
							if(profile.casinos[k] == datain.casino){
								incasinos = 1;
							}
						}
						if(incasinos == 1){
							res.send("error");
						}
						else{
							profile.casinos.push(datain.casino);
							addcasino(datain.username, profile.casinos);
							var gms = '';
							var trns = '';
							var spcs = '';
							//for all in pokergames, append to string gms
								for(var k in item.pokergames){
									gms = gms + ' ' + item.pokergames[k]; 
								}
								for(var k in item.tournamenttimes){
									trns = trns + ' ' + item.tournamenttimes[k];
								}
								for(var k in item.specials){
									spcs = spcs + ' ' + item.specials[k];
								}
							var strout = 'The ' + item.name + ' Casino\n' + 'Location: ' + item.address + '\n card games are ' + gms /*+ '\n tournament times are ' + trns + '\n specials are ' + spcs*/;
							console.log("this happened\n");
							res.send(strout);
						}
					});
				});
			}
		}
	});
	
	//append casino to casino array
	//addcasino...
	//addcasino("taylor", ['Bellagio', 'MGM Grand', 'Aria Resort', 'Morongo', 'Red Dragon']);
});

app.get('/removecasino', function(req, res, next){
	var datain = {};
	datain = req.query;
	console.log(datain.username);
	console.log(datain.password);
	console.log(datain.casino);
	//if casino name exists
	var newcasarray = [];
	var changecasinos = 0;
	getprofile(datain.username, datain.password, function(profile){
		for(var k in profile.casinos){
			if(profile.casinos[k] != datain.casino){
				newcasarray.push(profile.casinos[k]);
			}
			else if(profile.casinos[k] == datain.casino){
				changecasinos = 1;
			}
		}
		if(changecasinos == 0){
			res.send("error");
		}
		else{
			addcasino(datain.username, newcasarray);
			var gms = '';
			var trns = '';
			var spcs = '';
			//for all in pokergames, append to string gms
			res.send("successful");
		}
	});
				
	
	//append casino to casino array
	//addcasino...
	//addcasino("taylor", ['Bellagio', 'MGM Grand', 'Aria Resort', 'Morongo', 'Red Dragon']);
});

app.use(function(req,res){
  res.status(404);
  res.render('404');
});

app.use(function(err, req, res, next){
  console.error(err.stack);
  res.type('plain/text');
  res.status(500);
  res.render('500');
});

app.listen(app.get('port'), function(){
  console.log('Express started on http://localhost:' + app.get('port') + '; press Ctrl-C to terminate.');
});

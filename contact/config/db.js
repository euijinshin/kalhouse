var mysql = require('mysql');

var connection = mysql.createConnection({
    host:'localhost',
    user:'root',
    password:'1234',
    port:3306,
    database:'memo'
});

connection.connect();

connection.query('SELECT * FROM memos', function(error, results, fields){
    if(error){
        console.log(error);
    }
    console.log(results);
});

connection.end();

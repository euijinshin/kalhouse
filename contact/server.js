const express = require('express');

const app = express();

app.use(express.json());

var bodyParser = require('body-parser')

app.use(bodyParser.urlencoded({extended: false}))

var mysql = require('mysql');
var db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password:'1234',
    database:'person'
});

db.connect();

app.get('/test', (req, res) => {
    res.send("AAAAAAAAAAAA");
});

app.get('/get', function(req, res){
    
    console.log('/get request');

         db.query(`SELECT * FROM info WHERE name =?`,[req.query.name],  function(err,result){
            console.log('/get query success: ', result);
            res.send(result);
            
     })
       
});

app.get('/get/id', function(req, res){
    
    db.query(`SELECT * FROM info WHERE ID =?`,[req.query.id],  function(err,result){
       var dataList = [];
       for(var data of result){
           dataList.push(data.IMG);
           dataList.push(data.NAME);
       }
       res.send(dataList);
       console.log(dataList);
       console.log('정보 전달');
       
})
  
});

 app.post('/post', function(req,res){

    var users =[req.body.NAME, req.body.IMG, req.body.GENDER];
    console.log('/post request' + users);
    
    db.query(`INSERT INTO info(NAME, IMG, GENDER) VALUES(?, ?, ?);`,users, function(err, topics){
        if(err){
             console.log(err);
             res.writeHead(500);
             res.send('Internal Server Error');
        } else {
            console.log('/post request success');
            res.send("success")
        }
        
      /*  db.query(`SELECT NAME, AGE, GENDER FROM info WHERE NAME='shin';`, (error, rows) =>{ 
            console.log(rows);
            console.log(rows[0]);
            res.send(rows[0]);
        });*/
    })
 })

 app.post('/post/room', function(req, res){
    var room =[req.body.user_id, req.body.roomName, req.body.exitTime, req.body.img]
    db.query(`INSERT INTO room_info(user_id, roomName, exitTime, img) VALUES(?, ?, ?, ?);`, room, function(err, result){
        if(err){
            console.log(err);
            res.status(500).send('Internal Server Error');
        }
        console.log('정보 삽입');
       res.send("success")
    })
 })

 app.get('/get/room', function(req, res){
    
     
//     db.query(`SELECT room_info.id, room_info.roomName, info.name FROM room_info JOIN info ON room_info.user_id = info.id`, function(err,result){
       
//        res.send(result);
//        console.log(result);
       
// })
db.query(`SELECT * FROM room_info JOIN info ON room_info.user_id = info.id`, function(err,result){
        res.send(result);
        console.log(result);
    })
  
});
app.get('/get/myroom', function(req, res){
    db.query(`SELECT * FROM room_info JOIN info ON (room_info.user_id = info.id and room_info.id =?)
    `, [req.query.id], function(err,result){
        console.log(req.query.body);
        res.send(result);
    })
})

// app.get('/get/other_id', function(req, res){
//     db.query(`UPDATE room_info SET other_id=req.body.user_id WHERE id = req.body.id`, function(err, result){
//         res.send(result);
//         console.log(result);
//         console.log("zz");
//     })
// })

app.get('/get/user_id', function(req, res){
    db.query(`SELECT * FROM room_info JOIN info ON (room_info.user_id = info.id and room_info.user_id =?)
    `,[req.query.user_id], function(err, result){
        res.send(result);
        console.log(result);
    })
})

app.post('/post/other_id', function(req, res){
    db.query(`UPDATE room_info SET other_id= ?  WHERE id = ?`,[req.body.user_id, req.body.id] ,function(err, result){
        res.send(result);
        console.log(result);
    })
})

app.get('/get/other_id', function(req, res){
    db.query(`SELECT * FROM room_info JOIN info ON room_info.other_id = info.id and room_info.user_id=?`
    ,[req.query.user_id], function(err, result){
        res.send(result);
        console.log(result);
    })
})



app.listen(443, ()=>{
    console.log('포트번호 443서버접속')
})
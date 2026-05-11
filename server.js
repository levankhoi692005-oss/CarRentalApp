const express = require("express");

const mysql = require("mysql2");

const cors = require("cors");

const app = express();

app.use(cors());

app.use(express.json());

// ==============================
// MYSQL
// ==============================

const db = mysql.createPool({

    host: "localhost",

    user: "root",

    password: "123456789",

    database: "carrentalapp",

    waitForConnections: true,

    connectionLimit: 10,

    queueLimit: 0

});

console.log("✅ MySQL Connected");

// ==============================
// TEST SERVER
// ==============================

app.get("/", (req, res) => {

    res.send("Server OK");

});

// ==============================
// REGISTER
// ==============================

app.post("/register", (req, res) => {

    const {

        name,
        phone,
        password

    } = req.body;

    // CHECK PHONE EXISTS

    db.query(

        "SELECT * FROM users WHERE phone=?",

        [phone],

        (err, result) => {

            if(err){

                console.log(err);

                res.json({
                    success:false,
                    message:"Lỗi server"
                });

            }
            else{

                // PHONE EXISTS

                if(result.length > 0){

                    res.json({

                        success:false,

                        message:"Số điện thoại đã tồn tại"

                    });

                }

                // INSERT USER

                else{

                    db.query(

                        "INSERT INTO users(name,phone,password) VALUES(?,?,?)",

                        [
                            name,
                            phone,
                            password
                        ],

                        (err2, result2) => {

                            if(err2){

                                console.log(err2);

                                res.json({
                                    success:false,
                                    message:"Lỗi đăng ký"
                                });

                            }
                            else{

                                res.json({
                                    success:true
                                });

                            }

                        }

                    );

                }

            }

        }

    );

});

// ==============================
// LOGIN
// ==============================

app.post("/login", (req, res) => {

    const {

        phone,
        password

    } = req.body;

    db.query(

        "SELECT * FROM users WHERE phone=? AND password=?",

        [
            phone,
            password
        ],

        (err, result) => {

            if(err){

                console.log(err);

                res.json({
                    success:false
                });

            }
            else{

                if(result.length > 0){

                    res.json({

                        success:true,

                        name: result[0].name

                    });

                }
                else{

                    res.json({
                        success:false
                    });

                }

            }

        }

    );

});

// ==============================
// THÊM XE
// ==============================

app.post("/addvehicle", (req, res) => {

    const {

        hinhxe1,
        hinhxe2,
        tenxe,
        bienso,
        dongia,
        mota

    } = req.body;

    db.query(

        "INSERT INTO dulieuxe(hinhxe1,hinhxe2,tenxe,bienso,dongia,mota) VALUES(?,?,?,?,?,?)",

        [

            hinhxe1,
            hinhxe2,
            tenxe,
            bienso,
            dongia,
            mota

        ],

        (err, result) => {

            if(err){

                console.log(err);

                res.json({
                    success:false
                });

            }
            else{

                res.json({
                    success:true
                });

            }

        }

    );

});

// ==============================
// LẤY XE
// ==============================

app.get("/vehicles", (req, res) => {

    db.query(

        "SELECT * FROM dulieuxe",

        (err, result) => {

            if(err){

                console.log(err);

                res.json([]);

            }
            else{

                res.json(result);

            }

        }

    );

});

// ==============================
// XÓA XE
// ==============================

app.delete("/deletevehicle/:id", (req, res) => {

    const id = req.params.id;

    db.query(

        "DELETE FROM dulieuxe WHERE id=?",

        [id],

        (err, result) => {

            if(err){

                console.log(err);

                res.json({
                    success:false
                });

            }
            else{

                res.json({
                    success:true
                });

            }

        }

    );

});

// ==============================
// UPDATE XE
// ==============================

app.put("/updatevehicle/:id", (req, res) => {

    const id = req.params.id;

    const {

        hinhxe1,
        hinhxe2,
        tenxe,
        bienso,
        dongia,
        mota

    } = req.body;

    db.query(

        "UPDATE dulieuxe SET hinhxe1=?, hinhxe2=?, tenxe=?, bienso=?, dongia=?, mota=? WHERE id=?",

        [

            hinhxe1,
            hinhxe2,
            tenxe,
            bienso,
            dongia,
            mota,
            id

        ],

        (err, result) => {

            if(err){

                console.log(err);

                res.json({
                    success:false
                });

            }
            else{

                res.json({
                    success:true
                });

            }

        }

    );

});

// ==============================
// ĐẶT XE
// ==============================

app.post("/datxe", (req, res) => {

    const {

        bienso,
        hoten,
        sodienthoai,
        ngaydat,
        ngaylay,
        songaythue,
        dongia,
        thanhtien,
        thanhtoan,
        tinhtrang,
        ghichu

    } = req.body;

    db.query(

        "INSERT INTO datxe(bienso,hoten,sodienthoai,ngaydat,ngaylay,songaythue,dongia,thanhtien,thanhtoan,tinhtrang,ghichu) VALUES(?,?,?,?,?,?,?,?,?,?,?)",

        [

            bienso,
            hoten,
            sodienthoai,
            ngaydat,
            ngaylay,
            songaythue,
            dongia,
            thanhtien,
            thanhtoan,
            tinhtrang,
            ghichu

        ],

        (err, result) => {

            if(err){

                console.log(err);

                res.json({
                    success:false
                });

            }
            else{

                res.json({
                    success:true
                });

            }

        }

    );

});

// ==============================
// SERVER
// ==============================

app.listen(3000, () => {

    console.log("🚀 Server running at port 3000");

});
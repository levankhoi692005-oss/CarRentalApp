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

    console.log("✅ MySQL Connected -- OK");

    // ==============================
    // TEST SERVER
    // ==============================

    app.get("/", (req, res) => {

        res.send("Server OK");

    });

    // ==============================
    // REGISTER hhhh
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
                        console.log("Tai khoan co id: "+phone+" da dang nhap")

                        res.json({

                            success:true,
                             id: result[0].id,

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

        
        console.log("BODY DATA:");

    console.log(req.body);

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
        ghichu,
        user_id,
        tenxe,
        diachikh,
        diachinhan

    } = req.body;

    console.log("TEN XE:", tenxe);

        // Đếm số đơn của user

        db.query(
            "SELECT COUNT(*) AS total FROM datxe WHERE user_id=?",
            [user_id],
            (errcount,resultcount)=>
            {
                console.log("user_id: "+user_id);
                if(errcount)
                {
                    res.json({
                        success:false
                    });
                }
                else{
                    // STT DON
                    const stt_don = resultcount[0].total +1;
                    
                    // ma don
                    const madon = "MD"+user_id+stt_don;
                    console.log("stt_don: "+ stt_don);
                    console.log("madon: "+ madon);

                    // INSERT vao csdl

                    db.query(
                        `INSERT INTO datxe(
                        madon,
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
                        ghichu,
                        user_id,
                        tenxe,
                        diachikh,
                        diachinhan
                    )
                        VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)`,
                        [
                            madon,
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
                        ghichu,
                        user_id,
                        tenxe,
                        diachikh,
                        diachinhan
                        ],
                        (err,result)=>
                        {


                            if(err)
                            {
                                console.log(err);
                                res.json({
                                    
                                    success:false
                                });

                            }
                            else{
                                console.log("INSERT ĐƠN THÀNH CÔNG");
                                res.json({
                                    success:true,
                                    madon: madon
                                });

                            }
                        }
                    
                    
                    );
                }
            }
        )
        

        });
    



    
    // ==============================
// GET ĐƠN THEO USER
// ==============================

    app.get("/datxe/:user_id",(req,res)=>
{

    const user_id = req.params.user_id;

    db.query(

        "SELECT * FROM datxe WHERE user_id=? ORDER BY id DESC",

        [user_id],

        (err,result)=>
        {

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


 // Huy don: neu chua phe duyet thi huy dc , phe duyet roi khong huy dc

//  app.put("/cancelorder/:id", (req,res) =>
// {
//     const id = req.params.id;

//     // kiem tra trang thai don hang
//     db.query(
//         "SELECT * FROM datxe Where id=?",
//         [id],
//         (err,result) =>{

//             if(err){
//                 console.log(err);

//                 res.json({
//                     success:false
//                 });

                
//             }
//             else{
//                 if(result.length > 0){
//                     const order = result[0];

//                     // da duyet
//                     if(order.tinhtrang==="Đã duyệt"){
//                         res.json({
//                             success: false,
//                             message:"Đơn đã duyệt, không thể hủy"
//                         });
//                     }
//                     // chua duyet
//                     else
//                     {
//                         db.query(
//                             "UPDATE datxe SET tinhtrang=? WHERE id=?",
//                             ["Đã hủy", id],
//                             (err2,result2)=>{
//                                 if(err2){
//                                     console.log(err2);
//                                     res.json({
//                                         success:false

//                                     });
//                                 }
//                                 else{
//                                     res.json({
//                                         success:true
//                                     });
//                                 }
//                             }
//                         )
//                     }
//                 }
//                 else
//                 {
//                     res.json({
//                         success:false,
//                         message:"khong tim thay don"
//                     });
//                 }
//             }
//         }

//     );

// });


app.put("/cancelorder/:id", (req,res) =>
{

    try{

        const id = req.params.id;

        console.log("===== HỦY ĐƠN =====");

        console.log("ID ĐƠN:", id);

        // KIỂM TRA ĐƠN

        db.query(

            "SELECT * FROM datxe WHERE id=?",

            [id],

            (err,result) =>{

                if(err){

                    console.log("LỖI SELECT:");

                    console.log(err);

                    return res.json({
                        success:false
                    });

                }

                // KHÔNG TÌM THẤY

                if(result.length <= 0){

                    console.log("KHÔNG TÌM THẤY ĐƠN");

                    return res.json({

                        success:false,

                        message:"Không tìm thấy đơn"

                    });

                }

                const order = result[0];

                console.log("TRẠNG THÁI:", order.tinhtrang);

                // ĐÃ DUYỆT

                if(order.tinhtrang === "Đã duyệt"){

                    console.log("ĐƠN ĐÃ DUYỆT");

                    return res.json({

                        success:false,

                        message:
                                "Đơn đã duyệt, không thể hủy"

                    });

                }

                // HỦY ĐƠN

                db.query(

                    "UPDATE datxe SET tinhtrang=? WHERE id=?",

                    [

                        "Đã hủy",

                        id

                    ],

                    (err2,result2)=>{

                        if(err2){

                            console.log("LỖI UPDATE:");

                            console.log(err2);

                            return res.json({
                                success:false
                            });

                        }

                        console.log("HỦY ĐƠN THÀNH CÔNG");

                        res.json({
                            success:true
                        });

                    }

                );

            }

        );

    }
    catch(e){

        console.log("LỖI SERVER:");

        console.log(e);

        res.json({
            success:false
        });

    }

});


app.put("/changepassword",(req,res)=>
{
    const  {
        user_id,
        old_password,
        new_password

    } = req.body;

    db.query(
        'SELECT * FROM users WHERE id=? AND password=?',
        [user_id,old_password],
        (err,result)=>{

            if(err)
            {
                console.log(err);
                res.json({
                    success:false,
                    message:"Loi server"
                });

            }else{
                // neu khong tim thay
                if( result.length ==0)
                {
                    res.json({
                        success:false,
                        message:"Mật khẩu cũ sai"
                    });

                }
                else
                {
                    // update mat khau
                    db.query(

                        "UPDATE users SET password=? WHERE id=?",
                        [new_password,user_id],
                        (err2,result2)=>
                        {
                            if(err2)
                            {
                                console.log(err2);
                                res.json({
                                    success:false
                                });
                            }
                            else{
                                res.json({
                                    success:true,
                                    message:"Cập nhập mật khẩu thành công"
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
    // SERVER
    // ==============================

    app.listen(3000, () => {

        console.log("🚀 Server running at port 3000");

    });
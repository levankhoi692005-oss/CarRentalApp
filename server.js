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
            mota,
            mau,
            soghe,
            namsanxuat,
            hopso,
            congsuat,
            nhienlieu

        } = req.body;

        db.query(

            "INSERT INTO dulieuxe(hinhxe1,hinhxe2,tenxe,bienso,dongia,mota,mau,soghe,namsanxuat,hopso,congsuat,nhienlieu) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)",

            [

                hinhxe1,
                hinhxe2,
                tenxe,
                bienso,
                dongia,
                mota,
                mau,
                soghe,
                namsanxuat,
                hopso,
                congsuat,
                nhienlieu

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
            mota,
            mau,
            soghe,
            namsanxuat,
            hopso,
            congsuat,
            nhienlieu

        } = req.body;

        console.log("xe so: "+id);

        db.query(

            "UPDATE dulieuxe SET hinhxe1=?, hinhxe2=?, tenxe=?, bienso=?, dongia=?, mota=?, mau=?,soghe=?,namsanxuat=?,hopso=?,congsuat=?, nhienlieu=?  WHERE id=? ",

            [

                hinhxe1,
                hinhxe2,
                tenxe,
                bienso,
                dongia,
                mota,
                mau,
                soghe,
                namsanxuat,
                hopso,
                congsuat,
                nhienlieu,
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



app.put("/updateorder/:madon", (req,res) =>
{

    try{

        const madon = req.params.madon;
        const 
        {
            tinhtrang,
            thanhtoan
        } = req.body;

        
        let updatevalue=[];
        let updatefile =[];

        if(!madon)
        {
            return  res.json({

                success:false,
                message:"Thiếu ma đơn"
            });
        }

        if(tinhtrang)
        {
            updatefile.push("tinhtrang=?");
            
            updatevalue.push(tinhtrang);
        }

        
        if(thanhtoan)
        {
            updatefile.push("thanhtoan=?");
            
            updatevalue.push(thanhtoan);
        }

        

        if(updatefile.length<=0)
        {
            return  res.json({

                success:false,
                message:"không có gì để cập nhập"
            });
        }

        updatevalue.push(madon)

        const sql = `UPDATE datxe SET ${updatefile.join(", ")} WHERE madon=?`


        db.query(
            sql,
            updatevalue,
            (err,result)=>
            {
                if(err)
                {

                    console.log(err);

                    return res.json({
                        success:false
                    });

                }


                res.json({

                    success:true,

                    message:"Cập nhật thành công"

                });
            }

        )
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


//get anh xe theo bienso xe


    app.get("/imagevehicle/:bienso",(req,res)=>
    {

        const bienso = req.params.bienso;

        db.query("SELECT hinhxe1, hinhxe2 FROM dulieuxe WHERE bienso=? ",
            [bienso],
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
                    
                    res.json({
                        success:true,
                        hinhxe1:result[0].hinhxe1,
                        hinhxe2:result[0].hinhxe2
                    });
                }
            }
        );

    });



    // lay chi tiết đơn hành theo madon


    app.get("/detailorder/:madon",(req,res)=>
    {

        const madon = req.params.madon;

        db.query("SELECT * FROM dulieuxe JOIN datxe ON dulieuxe.bienso = datxe.bienso WHERE madon=?",
            [madon],
            (err,result)=>
            {
                if(err)
                {
                    console.log(err);
                    res.json({

                        success:false
                    });
                }
                else
                {
                    res.json({

                        success:true,
                        data:result
                    });

                }
            }
        );

    });


    

    app.get("/allmadon",(req,res) =>
{

    db.query("SELECT madon FROM datxe",(err,result)=>
    {

        if(err)
        {
            console.log(err);
            res.json({

                success:false
            });
        }
        else{
            res.json(
                {
                    success:true,
                    data:result
                }
            );
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
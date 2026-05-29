    const express = require("express");

    const mysql = require("mysql2");

    const cors = require("cors");

    const bcrypt = require("bcrypt");

    const crypto = require("crypto");

    require("dotenv").config();

    const {OpenAI} = require("openai");

    const mailer = require("nodemailer");






    const secret_key = "12345678901234567890123456789012";
    const IV_length = 16;



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


    const client = new OpenAI({

        apiKey: process.env.GITHUB_TOKEN,
        baseURL:"https://models.github.ai/inference"
    }
    );



    const transporter = mailer.createTransport({

        service:"gmail",
        auth:{
            user:'levankhoi060905@gmail.com',
            pass:'jfquyladjsspdtqa'
        }
    });

    async function sendemail(to, subject, html) {

    const info = await transporter.sendMail({
        from: '"Công ty ABC" <levankhoi060905@gmail.com>',
        to: to,
        subject: subject,
        html: html
    });

    return info;
}


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

    app.post("/register", async (req, res) => {

        const {

            name,
            phone,
            password

        } = req.body;

        // CHECK PHONE EXISTS

        db.query(

            "SELECT * FROM users WHERE phone=?",

            [phone],

            async (err, result) => {

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

                        const password_hash = await bcrypt.hash(password,10);

                        db.query(

                            "INSERT INTO users(name,phone,password) VALUES(?,?,?)",

                            [
                                name,
                                phone,
                                password_hash
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

    app.post("/login", async (req, res) => {

        const {

            phone,
            password

        } = req.body;

        

        db.query(

            "SELECT * FROM users WHERE phone=?",

            [
                phone
            ],

            async (err, result) => {

                if(err){

                    console.log(err);

                    res.json({
                        success:false
                    });

                }
                else{

                    if(result.length > 0){
                        const password_right = await bcrypt.compare(password,result[0].password);
                        if(password_right)
                        {
                            console.log("Tai khoan co id: "+result[0].id+" da dang nhap")

                        res.json({

                            success:true,
                             id: result[0].id,

                            name: result[0].name

                        });
                        }
                        else
                        {
                             res.json({
                            success:false,
                            message:"Mật khẩu hoặc số điện thoại không đúng"
                        });
                        }
                        

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

        "SELECT *, DATE_FORMAT(ngaydat, '%d:%m:%Y %H:%i:%s') AS ngaydat_format,  DATE_FORMAT(ngaylay, '%d:%m:%Y') AS ngaylay_format FROM datxe WHERE user_id=? ORDER BY id DESC",

        [user_id],

        (err,result)=>
        {

            if(err){

                console.log(err);

                res.json([]);

            }
            else{

                res.json(result);
                console.log("Ket qua lay user theo order" + result.ngaydat_format);
                console.log("Ket qua lay user theo order" + result.ngaylay_format);

            }

        }

    );

});

// chinh chinh sach cap nhap don hang

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


app.put("/changepassword",async (req,res)=>
{
    const  {
        user_id,
        old_password,
        new_password

    } = req.body;


    db.query(`SELECT password FROM users WHERE id=?`,
        [user_id],
        async (err1,result1)=>
        {

            if(err1)
            {
                console.log(err1);
                res.json({

                    success:false,
                    message:"lỗi"
                });


            }
            else
            {
                if(result1.length <= 0)
                {
                    res.json({

                    success:false,
                    message:"Không đúng mật khẩu"
                });

                }
                else
                {

                    const check_password = 
                    await bcrypt.compare(old_password,result1[0].password)

                    if(check_password)
                    {

                        const new_pass = await bcrypt.hash(new_password,10);
                        db.query(`UPDATE users SET password=? WHERE id=?`,
                            [new_pass,user_id],
                            async (err2,result2)=>
                            {
                                if(err2)
                                {
                                    console.log(err1);
                                    res.json({

                                    success:false,
                                    message:"Không đúng user_id"
                                        });
                                }
                                else
                                {
                                    res.json({
                                        success:true,
                                        message:"Đổi mật khẩu thành công"
                                    });

                                }
                            }
                        );
                    }
                    else{
                        res.json({
                                        success:false,
                                        message:"Mật khẩu không đúng"
                                    });
                    }
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

        db.query ("SELECT * , DATE_FORMAT(ngaydat, '%d/ %m/ %Y - %H:%i:%s') AS ngaydat_format,  DATE_FORMAT(ngaylay, '%d/ %m/ %Y') AS ngaylay_format FROM dulieuxe JOIN datxe ON dulieuxe.bienso = datxe.bienso WHERE madon=?",
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

    db.query("SELECT madon,tinhtrang,bienso, DATE_FORMAT(DATE_ADD(ngaylay, INTERVAL songaythue DAY),'%d/%m/%Y') AS ngaytraxe FROM datxe",(err,result)=>
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






app.post("/chatai/:user", async (req,res)=>
{

    try
    {

        // =========================
        // USER NAME
        // =========================

        const user_name = req.params.user;

        // =========================
        // MESSAGE
        // =========================

        const message_ai = req.body.message;
        
        const send_time = req.body.send_time;

        // =========================
        // CHECK EMPTY
        // =========================

        if(!message_ai || message_ai.trim()==="")
        {

            return res.json({

                success:false,

                answer:"Bạn chưa nhập tin nhắn"

            });

        }

        // =========================
        // SAVE USER MESSAGE
        // =========================

        db.query(

            "INSERT INTO messages(user,message,send_time) VALUES(?,?,?)",

            [
                user_name+"_AI",
                message_ai,
                send_time
            ]

        );

        // =========================
        // GET VEHICLES
        // =========================

        db.query(

            "SELECT * FROM dulieuxe",

            (err,vehicles)=>
            {

                if(err)
                {

                    console.log(err);

                    return res.json({

                        success:false,

                        answer:"Lỗi lấy dữ liệu xe"

                    });

                }

                // =========================
                // GET USER ID
                // =========================

                db.query(

                    "SELECT id FROM users WHERE name=?",

                    [user_name],

                    (err2,result)=>
                    {

                        if(err2)
                        {

                            console.log(err2);

                            return res.json({

                                success:false,

                                answer:"Lỗi lấy user"

                            });

                        }

                        // =========================
                        // CHECK USER
                        // =========================

                        if(result.length <=0)
                        {

                            return res.json({

                                success:false,

                                answer:"Không tìm thấy user"

                            });

                        }

                        // =========================
                        // USER ID
                        // =========================

                        const user_id =
                            result[0].id;

                        console.log(
                            "Đã lấy id từ name: "
                            +user_name+
                            " id: "+
                            user_id
                        );

                        // =========================
                        // GET ORDERS
                        // =========================

                        db.query(

                            "SELECT * FROM datxe WHERE user_id=?",

                            [user_id],

                            async (err3,orders)=>
                            {

                                if(err3)
                                {

                                    console.log(err3);

                                    return res.json({

                                        success:false,

                                        answer:"Lỗi lấy đơn hàng"

                                    });

                                }

                                // =========================
                                // PROMPT
                                // =========================

                                const data_send_ai =
                                `
                                Bạn là AI chatbot thuê xe.

                                Tôi sẽ cung cấp dữ liệu hệ thống.

                                =========================
                                THÔNG TIN CÔNG TY
                                =========================

                                Tên công ty:
                                Công ty CP thuê xe MMT01

                                Địa chỉ:
                                Đại học công nghiệp hà nội

                                Email:
                                ctymmt01@gmail.com

                                Số điện thoại:
                                0978613522

                                Chủ tịch:
                                Lê Văn Khởi

                                =========================
                                DANH SÁCH XE
                                =========================

                                ${JSON.stringify(vehicles)}

                                =========================
                                ĐƠN HÀNG USER
                                =========================

                                ${JSON.stringify(orders)}

                                =========================
                                USER HỎI
                                =========================

                                ${message_ai}

                                Hãy trả lời đúng dữ liệu hệ thống.
                                `;

                                // =========================
                                // AI
                                // =========================

                                let response;

                                try
                                {

                                    response =
                                    await client.chat.completions.create({

                                        model:"openai/gpt-4.1-mini",

                                        messages:[

                                            {

                                                role:"system",

                                                content:
                                                "Bạn là AI chatbot thuê xe"

                                            },

                                            {

                                                role:"user",

                                                content:data_send_ai

                                            }

                                        ]

                                    });

                                }
                                catch(aiError)
                                {

                                    console.log(aiError);

                                    return res.json({

                                        success:false,

                                        answer:"Lỗi AI"

                                    });

                                }

                                // =========================
                                // ANSWER
                                // =========================

                                const answer =
                                response.choices[0]
                                .message.content;

                                // =========================
                                // SAVE AI MESSAGE
                                // =========================

                                db.query(

                                    "INSERT INTO messages(user,message,send_time) VALUES(?,?,?)",

                                    [
                                        "AI_"+user_name,
                                        answer,
                                        send_time
                                    ]

                                );

                                // =========================
                                // RETURN
                                // =========================

                                return res.json({

                                    success:true,

                                    answer:answer

                                });

                            }

                        );

                    }

                );

            }

        );

    }
    catch(e)
    {

        console.log(e);

        return res.json({

            success:false,

            answer:"Lỗi server"

        });

    }

});




    app.get("/getmessagechat/:user_name", async (req,res)=>
    {

        const user_name = req.params.user_name;

        db.query(`SELECT *, DATE_FORMAT (send_time, '%H:%i %d:%m:%Y') AS send_time_format FROM messages WHERE user IN (?,?)`,
            [user_name+"_AI","AI_"+user_name],
            (err,result)=>
            {
                if(err)
                {
                    console.log(err);
                    res.json({
                        success:false,
                        message:"Lỗi truy xuất dữ liệu"
                    });
                }
                else
                {
                    if(result.length <=0)
                    {
                        res.json({

                            success:false,
                            message:"Lịch sử chat trống"
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
            }
        );


    });


    app.put("/update_profile/:id", (req,res)=>
    {

        const id = req.params.id;

        const  {
             name,
             gender,
             street_address,
             email,
             phone,
             birthday,
             national_id,
             license_number,
             license_class,
             license_expiry
        }= req.body;

        
        db.query(`UPDATE users SET name=?,
            gender=?,
            street_address=?,
            email=?,
             phone=?,
             birthday=?,
             national_id=?,
             driver_license_number=?,
             driver_license_class=?,
             driver_license_expiry=?
             WHERE id=?
            
            `,
        [name,
             gender,
             encrypt(street_address),
             encrypt(email),
             phone,
             birthday,
             encrypt(national_id),
             encrypt(license_number),
             license_class,
             license_expiry,
            id],
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
                        success:true
                    });
                }
            });



    });



    app.get("/get_user_profile/:id", (req,res)=>
    {

        const user_id = req.params.id;

        db.query(
            `SELECT name,
             gender,
             street_address,
             email,
             phone,
             birthday,
             national_id,
             driver_license_number,
             driver_license_class,
             driver_license_expiry,
             DATE_FORMAT(birthday, '%d/%m/%Y') AS birthday_format,
             DATE_FORMAT(driver_license_expiry, '%d/%m/%Y') AS license_expiry_format
             FROM users WHERE id=?`,
             [user_id],
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
                    if(result.length>0)
                    {

                        result[0].national_id = decrypt(result[0].national_id);
                         result[0].street_address = decrypt(result[0].street_address);
                          result[0].email = decrypt(result[0].email);
                           result[0].driver_license_number = decrypt(result[0].driver_license_number);

                    }
                    res.json({
                        success:true,
                        data:result
                    });
                    
                }

             }

        );

    });


    

    function encrypt(text)
    {

        const iv = crypto.randomBytes(IV_length);
        const cipher = crypto.createCipheriv(
            "aes-256-cbc",
            Buffer.from(secret_key),
            iv
        );

        let encrypted = cipher.update(
            text,
            "utf8",
            "hex"
        );

        encrypted+= cipher.final("hex");

        return iv.toString("hex") +":"+encrypted;

        
    }


    function decrypt(text)
    {

        const parts = text.split(":");
        const iv = Buffer.from(parts[0],"hex");
        const encrypted_text = parts[1];


        const decipher = crypto.createDecipheriv(
            "aes-256-cbc",
            Buffer.from(secret_key),
            iv
        );

        let decrypted = decipher.update(
            encrypted_text,
            "hex",
            "utf8"
        );


        decrypted += decipher.final("utf8");

        return decrypted;

    }




     app.get("/send_email/:madon", (req,res)=>
    {

        const ma_don = req.params.madon;

        db.query(
            `SELECT * FROM users JOIN datxe ON users.id = datxe.user_id WHERE madon=?`,
            [ma_don],
           async  (err,result)=>{

                if(err)
                {
                    console.log(err);
                    res.json({
                        success:false
                    });
                }
                else
                {
                    console.log(decrypt(result[0].email));
                    if(result.length <=0)
                    {
                       return res.json({
                            success:false,
                            message:"Không tìm thấy"
                        });
                    }
                    else
                    {
                        try
                        {

                             const email_user = decrypt(result[0].email);
                        if(result[0].tinhtrang === "Đã từ chối")
                        {
                            await sendemail(email_user,
                            "Xác nhận thuê xe",
                            `
                                <h2>Đặt xe không thành công</h2>

                                <p>Mã đơn: ${result[0].madon}</p>

                                <p>Khách hàng: ${result[0].hoten}</p>

                                <p>Số điện thoại: ${result[0].sodienthoai}</p>

                                <p>Xe thuê: ${result[0].tenxe}</p>

                                <p>Ngày đăng ký: ${result[0].ngaydat}</p>

                                <p>Ngày nhận xe: ${result[0].ngaylay}</p>

                                <p>Số ngày thuê: ${result[0].songaythue}</p>

                                <p>Tổng tiền: ${result[0].thanhtien} VNĐ</p>
                                `

                        );

                        }else  if(result[0].tinhtrang === "Đã duyệt")
                        {
                            await sendemail(email_user,
                            "Xác nhận thuê xe",
                            `
                                <h2>Đặt xe thành công</h2>

                                <p>Mã đơn: ${result[0].madon}</p>

                                <p>Khách hàng: ${result[0].hoten}</p>

                                <p>Số điện thoại: ${result[0].sodienthoai}</p>

                                <p>Xe thuê: ${result[0].tenxe}</p>

                                <p>Ngày đăng ký: ${result[0].ngaydat}</p>

                                <p>Ngày nhận xe: ${result[0].ngaylay}</p>

                                <p>Số ngày thuê: ${result[0].songaythue}</p>

                                <p>Tổng tiền: ${result[0].thanhtien} VNĐ</p>
                                `

                        );
                        }

                        

                        return res.json({
                            success:true
                        });


                        }
                        catch(e)
                        {
                            console.log(e);
                            return res.json({
                        success: false,
                        message: e.message
                         });
                        }

                       
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
package com.cocu.controller;


import com.cocu.common.Constant;
import com.cocu.common.Result;
import com.cocu.entity.User;
import com.cocu.service.MenuService;
import com.cocu.service.UserService;
import com.cocu.utils.EmailCodeGenerator;
import com.cocu.vo.AuthVO;
import com.cocu.vo.UserVO;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.imageio.ImageIO;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class AuthController {



    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Value("${me.upload.path}")
    private String baseFolderPath;

    @PostMapping("/upload")
    public Result upload(HttpServletRequest request, @RequestParam("uploadAvatar") MultipartFile image) {
        /*if(UserUtils.getCurrentUser() == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }*/

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        StringBuffer url = new StringBuffer();
        String filePath = sdf.format(new Date());

        File baseFolder = new File(baseFolderPath + filePath);
        if (!baseFolder.exists()) {
            baseFolder.mkdirs();
        }

        url.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath())
                .append("/")
                .append(filePath);

        String imgName = UUID.randomUUID() + "_" + image.getOriginalFilename().replaceAll(" ", "");

        try {

            File dest = new File(baseFolder, imgName);
            image.transferTo(dest);

            url.append("/").append(imgName);

            // result hashmap
            HashMap result = new HashMap(){{
                put("url", url);
            }};
            return Result.success(result, "Upload image successfully!");

        } catch (IOException e) {
            //logger.error("文件上传错误 , uri: {} , caused by: ", request.getRequestURI(), e);
            return Result.fail();
        }
    }


    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpServletRequest request){

        // check verification image code
        String imageCode = (String) request.getSession().getAttribute("imageCode");
        if(StringUtils.isEmpty(imageCode)){
            return Result.fail("Verification code expired!");
        }
        if(!imageCode.equals(user.getImageCode())){
            return Result.fail("Verification code incorrect!");
        }
        // get user by query account and password
        List<User> userList = userService.lambdaQuery()
                .eq(User::getEmail, user.getEmail())
                .eq(User::getPassword, DigestUtils.md5DigestAsHex(user.getPassword().getBytes()))
                .list();

        // query user menu
        if(userList.size() > 0){
            User userQuery = userList.get(0);
            UserVO userVO = new UserVO(
                    userQuery.getId(),
                    userQuery.getEmail(),
                    userQuery.getUsername(),
                    userQuery.getMobile(),
                    userQuery.getAvatar(),
                    userQuery.getGender(),
                    userQuery.getAge(),
                    userQuery.getRoleId()
            );
            // Generate JWT
            String jwtToken = Jwts.builder()
                    .setSubject(userQuery.getEmail())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                    .signWith(SignatureAlgorithm.HS512, "COCU_RYAN")
                    .compact();
            Map<String, Object> response = new HashMap<>();
            response.put("user", userVO);
            response.put("token", jwtToken);
            return Result.success(response, "Login successfully!");
        }
        return Result.fail("Account or password incorrect!");
    }


    @PostMapping("/register")
    public Result register(@RequestBody User user){
        //check parameter
        if(StringUtils.isBlank(user.getEmail()) || StringUtils.isBlank(user.getPassword())){
            return Result.fail("Invalid Email or Password!");
        }
        //check email duplicate
        List<User> userList = userService.lambdaQuery()
                .eq(User::getEmail, user.getEmail())
                .list();
        if(userList.size() > 0){
            return Result.fail("Email account already exist!");
        }

        // encrypt password
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        // user basic information
        user.setUsername(user.getUsername());
        if(StringUtils.isNotBlank(user.getMobile())){
            user.setMobile(user.getMobile());
        }
        if(user.getGender() != null){
            user.setGender(user.getGender());
        }
        if(user.getAge() != null){
            user.setAge(user.getAge());
        }
        user.setAvatar(Constant.DEFAULT_USER_AVATAR);
        user.setSalt(Constant.DEFAULT_SALT);
        user.setRoleId(3L);

        if(userService.save(user)){
            return Result.success("Register successfully!");
        }

        return Result.fail("Register failed, please try again!");
    }


    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @PostMapping("/imageCode")
    public Result imageCode(HttpServletRequest request){
        // generate verification code
        String code = defaultKaptcha.createText();
        // store code in session
        HttpSession session = request.getSession();
        session.setAttribute("imageCode", code);
        // generate image, convert to base64
        BufferedImage bufferedImage = defaultKaptcha.createImage(code);
        ByteArrayOutputStream outputStream = null;
        try{
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(outputStream.toByteArray());
            String captchaBase64 = "data:image/jpeg;base64," + base64.replaceAll("\r\n", "");
            Result result = Result.success(captchaBase64, "Generate image code successfully!");
            return result;
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(outputStream != null){
                    outputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @PostMapping("/forgetPwd")
    public Result forgetPwd(@RequestBody AuthVO authVO, HttpSession session) {
        // Check if the email and new password are provided
        if (StringUtils.isBlank(authVO.getEmail()) || StringUtils.isBlank(authVO.getNewPwd())) {
            return Result.fail("Email or new password cannot be blank");
        }

        // Retrieve the user by email
        User user = userService.lambdaQuery().eq(User::getEmail, authVO.getEmail()).one();

        if (user == null) {
            return Result.fail("Email not registered");
        }

        // Update the user's password
        user.setPassword(DigestUtils.md5DigestAsHex(authVO.getNewPwd().getBytes()));
        boolean updateSuccess = userService.updateById(user);

        if (updateSuccess) {
            // Clean up session attributes
            return Result.success("Password reset successfully");
        } else {
            return Result.fail("Failed to reset password, please try again");
        }
    }

    @PostMapping("/checkEmailCode")
    public Result checkEmailCode(@RequestBody AuthVO authVO, HttpSession session) {
        String sessionCode = (String) session.getAttribute("emailCode");
        String sessionEmail = (String) session.getAttribute("email");

        if (sessionCode != null && sessionEmail != null
                && sessionCode.equals(authVO.getEmailCode())
                && sessionEmail.equals(authVO.getEmail())) {
            // Verification code matches, proceed with password reset
            // Reset the password logic here
            session.removeAttribute("emailCode"); // Clean up session attributes
            session.removeAttribute("email");

            return Result.success("Bingo! Verification code is correct!");
        } else {
            return Result.fail("Invalid verification code or email");
        }
    }


    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    // read sender email address from properties file
    @Value("${spring.mail.username}")
    private String from;

    /**
     * send simple email
     * @param to receiver email address
     * @param subject email subject
     * @param text email content
     */
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);  // email sender
        message.setTo(to);  // email receiver
        message.setSubject(subject);  // email subject
        message.setText(text);  // email content
        javaMailSender.send(message);  // send email
    }

    @PostMapping("/sendTemplateMail")
    public Result sendTemplateMail(@RequestBody AuthVO authVO, HttpSession session){
        String from = "1342502141@qq.com";
        String to = authVO.getEmail();
        String subject = "COCU - Verify its you";
        String message = "You are trying to reset password, if its not your operation, please ignore.";
        String code = EmailCodeGenerator.generateVerificationCode();
        // Store the code in the session
        session.setAttribute("emailCode", code);
        session.setAttribute("email", authVO.getEmail());

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("code", code);
        if(sendTemplateMail(from, to, subject, context)){
            return Result.success("Send verification email successfully!");
        }else{
            return Result.fail("Fail to send verification email!");
        }
    }


    public Boolean sendTemplateMail(String from, String to, String subject, Context context) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
            String mail = templateEngine.process("email.html",  context);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(mail,true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

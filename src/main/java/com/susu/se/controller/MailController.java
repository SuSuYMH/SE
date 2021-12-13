package com.susu.se.controller;

import com.susu.se.service.ActivationService;
import com.susu.se.utils.Return.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RestController
@RequestMapping("mails")
public class MailController {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ActivationService activationService;

    //发送邮件
    @PostMapping(value = "/sendMail")
    public Result<String> sendMail(@RequestParam("userId") Integer userId) {
        return activationService.activationMail(userId);
    }

    //根据验证码，激活账户！
    @PostMapping("/identify")
    public Result<String> identifyCode(@RequestParam("code") String code,@RequestParam("userid") Integer userId){
        return activationService.identifyCode(code, userId);
    }

//    //发送普通文本邮件
//    @RequestMapping(value = "/sendMail")
//    public String sendMail(Model model) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("654321***@163.com"); //发送者
//        message.setTo("123456***@qq.com");  //接受者
//        message.setCc("654321***@163.com"); //抄送，填发送者的邮箱即可
//        message.setSubject("今天天气真好");	//主题
//        message.setText("你好你好你好！");	//内容
//        try {
//            javaMailSender.send(message);
//            System.out.println("简单邮件已经发送");
//        } catch (Exception e) {
//            System.out.println("发送简单邮件时发生异常！"+e.toString());
//        }
//        model.addAttribute("msg", "");
//        return "login";
//    }
//
//    //也可以html邮件
//    @RequestMapping("/sendHtmlMail")
//    public void sendHtmlMail() {
//        String content="<html>\n" +
//                "<body>\n" +
//                "    <h3>你好你好你好!</h3>\n" +
//                "</body>\n" +
//                "</html>";
//        MimeMessage message = javaMailSender.createMimeMessage();
//        try {
//            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
//            messageHelper.setFrom("654321***@163.com");
//            messageHelper.setTo("123456***@qq.com");
//            messageHelper.setSubject("今天天气真好");
//            messageHelper.setText(content, true);
//            javaMailSender.send(message);
//            System.out.println("邮件成功发送");
//        } catch (MessagingException e) {
//            System.out.println("发送邮件时发生异常！"+e.toString());
//        }
//    }

}

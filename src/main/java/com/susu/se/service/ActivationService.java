package com.susu.se.service;

import com.susu.se.model.users.User;
import com.susu.se.repository.UserRepository;
import com.susu.se.utils.Return.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ActivationService {
    //先引入RedisTemplate
    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    //向邮箱中发送验证码，并将email：验证码这个键值对存在redis中三分钟
    public Result<String> activationMail(Integer userId){
        //根据userid获取当前账号的邮箱
        String email = userRepository.findById(userId).get().getEmail();
        //验证邮箱格式是否正确
        String emailReg = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern pattern = Pattern.compile(emailReg);
        Matcher matcher = pattern.matcher(email);
        if(matcher.find()){		//验证邮箱格式是否正确
            String code = String.valueOf((int)((Math.random()*9+1)*1000));  //四位随机数验证码
            //向redis里存入数据和设置缓存时间
            redisTemplate.opsForValue().set(email, code, 60 * 3, TimeUnit.SECONDS);
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(from); //发送者
                message.setTo(email);  //接受者
                message.setCc("1809817487@qq.com"); //抄送，填发送者的邮箱即可
                message.setSubject("同济大学实验教学管理系统账号邮箱验证");	//主题
                message.setText("验证码为："+code+"有效时间为三分钟！");	//内容
                mailSender.send(message);

            } catch (Exception e) {
                e.printStackTrace();
                return Result.wrapErrorResult("邮件发送失败！");
            }
        }else {
            return Result.wrapErrorResult("邮件格式错误！");
        }
        return Result.wrapSuccessfulResult("发送验证码成功！").setMessage("发送验证码成功！");
    }

    //对邮箱发送的验证码进行确认
    public Result<String> identifyCode(String code,Integer userId){
        //根据userid获取当前账号的邮箱
        String email = userRepository.findById(userId).get().getEmail();

        System.out.println(redisTemplate.opsForValue().get(email));
        System.out.println("---------");
        System.out.println(code);
        if(redisTemplate.opsForValue().get(email)==null){
            return Result.wrapErrorResult("验证码过期，请重新发送！");
        }
        if(redisTemplate.opsForValue().get(email).equals(code)){
            //设置账号为激活状态
            Optional<User> byId = userRepository.findById(userId);
            User user = byId.get();
            user.setActivation(true);
            userRepository.save(user);
            return Result.wrapSuccessfulResult("账号激活成功").setMessage("账号激活成功！");
        }else{
            return Result.wrapErrorResult("验证码错误！");
        }

    }
}

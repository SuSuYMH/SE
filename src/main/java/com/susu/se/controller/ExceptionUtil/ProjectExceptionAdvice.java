package com.susu.se.controller.ExceptionUtil;

import com.susu.se.utils.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ProjectExceptionAdvice {
    @ExceptionHandler
    //这个注解会拦截所有的异常信息，所有的异常都会来这里解决，下面那个ex就是拦截到的异常对象;这个注解还可以加具体的异常种类，来处理特殊的异常
    public Result doException(Exception ex){
        //记录日志

        //通知运维

        //通知开发

        //控制台报信息
        ex.printStackTrace();

        //用户提示
        return new Result(false, "服务器故障，请稍后再试！");

    }
}

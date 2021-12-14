package com.susu.se.controller.ExceptionUtil;

import com.susu.se.utils.Return.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.NoSuchElementException;

@ControllerAdvice
@ResponseBody
public class ProjectExceptionAdvice {
    @ExceptionHandler
    //这个注解会拦截所有的异常信息，所有的异常都会来这里解决，下面那个ex就是拦截到的异常对象;这个注解还可以加具体的异常种类，来处理特殊的异常
    public Result<String> doException(Exception ex){
        //记录日志
        //通知运维
        //通知开发
        //控制台报信息
        //用户提示

        //操作权限不允许
        if(ex instanceof AuthorizationException){
            ex.printStackTrace();
            return Result.wrapErrorResult("无权操作！！！");
        }
        //请求实体不存在
        if(ex instanceof NoSuchElementException){
            ex.printStackTrace();
            return Result.wrapErrorResult("没有请求的实体，请查看请求的数据在数据库中是否存在！");
        }
        //对于上述没有列出的异常，直接在message中返回异常的类的名称
        return Result.wrapErrorResult(ex.getClass().getName());
    }
}

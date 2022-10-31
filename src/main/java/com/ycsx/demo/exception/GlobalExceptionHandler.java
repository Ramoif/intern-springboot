package com.ycsx.demo.exception;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.ycsx.demo.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

// 统一处理异常
@ControllerAdvice(basePackages = "com.ycsx.demo.controller")
public class GlobalExceptionHandler {
    private static final Log log = LogFactory.get();

    @ExceptionHandler(CustomException.class)    // 处理Custom异常类
    @ResponseBody                               // 返回json
    public Result<?> customer(HttpServletRequest request, CustomException e){
        return Result.error(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(Exception.class)          // 处理公共异常类
    @ResponseBody                               // 返回json
    public Result<?> error(HttpServletRequest request, Exception e) {
        log.error("异常信息：", e);
        return Result.error("-1", "系统异常");
    }
}

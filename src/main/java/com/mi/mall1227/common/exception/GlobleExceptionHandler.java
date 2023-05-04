package com.mi.mall1227.common.exception;

import com.mi.mall1227.common.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@ResponseBody
public class GlobleExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public R exceptionHandler(HttpServletRequest request, Exception e) {
        System.out.println("系统异常");
        return R.error("服务端异常，请联系管理员\"+\"<br/>" + e.getMessage());
    }
}

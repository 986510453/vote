package com.wechar.vote.exception;

import com.wechar.vote.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO 全局异常捕捉类
 *
 * @Name: PermissionExceptionHandler
 * @Auther: zzf
 * @Date: 2018/8/1 17:12
 */

@ControllerAdvice
public class PermissionExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(PermissionExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public void defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        Map<String, String> resultMap = new HashMap<String, String>();

        SpringUtil.out(response, resultMap);
    }

}

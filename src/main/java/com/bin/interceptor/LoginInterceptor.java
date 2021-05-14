package com.bin.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bin.common.Result;
import com.bin.util.HttpContextUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录拦截器
 * 通过GlobalCorsConfig 配置到框架
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")){
            return true;
        }

      /*  System.out.println(request.getRequestURI());
        if (request.getRequestURI().contains("/login") || request.getRequestURI().contains("/addDevice")){
            return true;
        }*/
        //从header中获取token
        String token = request.getHeader("token");
        //如果token为空
        if (StringUtils.isBlank(token)) {
            setReturn(response,401,"用户未登录，请先登录");
            return false;
        }
        //在实际使用中还会:
        // 1、校验token是否能够解密出用户信息来获取访问者
        // 2、token是否已经过期

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

    //返回json格式错误信息
    private static void setReturn(HttpServletResponse response, Integer code, String msg) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtil.getOrigin());
        //UTF-8编码
        httpResponse.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        Result result = new Result(code,msg,"");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);
        httpResponse.getWriter().print(json);
    }

}

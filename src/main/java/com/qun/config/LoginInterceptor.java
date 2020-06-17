package com.qun.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //登录成功之后，应该有用户的session
        Object uid = request.getSession().getAttribute("uid");
        if (uid==null){
            request.setAttribute("msg","没有权限，请先登录");
            request.getRequestDispatcher("/index").forward(request,response);
            return false;
        }else {
            return true;
        }

    }
}

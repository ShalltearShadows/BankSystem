package com.qun.config;

import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {
    //解析请求
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        //获取请求中的语言参数
        String language = request.getParameter("lang");

        Locale defaultLocale = Locale.getDefault();

        //如果语言参数不为空，使用用户请求的语言
        if (!StringUtils.isEmpty(language)){
            String[] split = language.split("_");
            defaultLocale = new Locale(split[0],split[1]);
        }

        return defaultLocale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}

package com.chl.handele;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 自定义处理器:formLogin().successHandler() 中需要
 * @author: chl
 * @date: 2022-04-21
 **/
@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * Spring 是使用jackson来进行处理返回数据的,所以这里可以得到他的实例
     */
    @Resource
    private ObjectMapper objectMapper;

    private final static String CONTENT_TYPE = "application/json;charset=UTF-8";

    /**
     * @param authentication 封装了所有的认证信息
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType(CONTENT_TYPE);
        response.getWriter().write(objectMapper.writeValueAsString(authentication));
    }
}

package com.chl.controller;

import com.chl.common.RestResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: chl
 * @date: 2022-04-21
 **/
@RestController
public class SecurityRequestController {
    /**
     * 封装了引发跳转请求的工具类，看实现类应该是从 session 中获取的
     */
    private final RequestCache requestCache = new HttpSessionRequestCache();

    /**
     * spring 的工具类：封装了所有跳转行为策略类
     */
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 当需要身份认证时跳转到这里
     */
    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public RestResult requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        // 如果有引发认证的请求
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            System.out.println(("引发跳转的请求：" + targetUrl));
            // 如果是 html 请求，则跳转到登录页
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                redirectStrategy.sendRedirect(request, response, "/login");
            }
        }
        // 否则都返回需要认证的 json 串
        return new RestResult("访问受限，请前往登录页面");
    }
}
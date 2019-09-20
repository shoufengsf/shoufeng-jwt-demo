package com.shoufeng.shoufengjwtdemo.configurer;

import com.alibaba.fastjson.JSON;
import com.shoufeng.shoufengjwtdemo.common.Result;
import com.shoufeng.shoufengjwtdemo.common.ResultCode;
import com.shoufeng.shoufengjwtdemo.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Spring MVC 配置
 *
 * @author zhihao.mao
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyWebMvcConfigurer.class);

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 添加拦截器
     *
     * @param registry 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                                     Object handler) throws Exception {
                //验证签名
                String token = request.getHeader("token");
                Map<String, Object> userInfoMap = jwtUtils.validateTokenAndGetClaims(token);
                System.out.println("token中包含的用户信息：" + JSON.toJSONString(userInfoMap));
                // TODO: 2019/9/20 可以根据拿到用户信息做校验
                if (userInfoMap != null) {
                    return true;
                } else {
                    LOGGER.warn("签名认证失败，请求接口：{}，请求参数：{}",
                            request.getRequestURI(),
                            JSON.toJSONString(request.getParameterMap()));

                    Result result = Result.builder().build();
                    result.setCode(ResultCode.UNAUTHORIZED).setMessage("签名认证失败");
                    responseResult(response, result);
                    return false;
                }
            }
            //登陆接口不进行校验
        }).excludePathPatterns("/login");
    }

    private void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

}

package com.shoufeng.shoufengjwtdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.shoufeng.shoufengjwtdemo.common.Result;
import com.shoufeng.shoufengjwtdemo.common.ResultCode;
import com.shoufeng.shoufengjwtdemo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shoufeng
 */
@RestController
public class LoginController {

    @Autowired
    private JwtUtils jwtUtils;

    @RequestMapping("/login")
    public Result login(String userName, String passWords) {
        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("userName", userName);
        infoMap.put("passWords", passWords);
        JSONObject tokenInfoJson = jwtUtils.generateToken(infoMap);
        return Result.builder().code(ResultCode.SUCCESS.code()).data(tokenInfoJson).build();
    }

    @RequestMapping("/test")
    public Result test() {
        return Result.builder().code(ResultCode.SUCCESS.code()).data("test 成功").build();
    }

}

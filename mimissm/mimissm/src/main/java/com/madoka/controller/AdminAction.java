package com.madoka.controller;


import com.madoka.pojo.Admin;
import com.madoka.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminAction {

    @Autowired
    private AdminService as;

    @RequestMapping("/login")
    public String login(String name, String pwd, HttpServletRequest request) {

        Admin admin = as.login(name,pwd);
        if(admin != null) {
            request.getSession().setAttribute("admin",admin);
            return "main";
        }else {
            request.setAttribute("errmsg","用户名或密码不正确");
            return "login";
        }
    }
}

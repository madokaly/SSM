package com.madoka.service.impl;

import com.madoka.mapper.AdminMapper;
import com.madoka.pojo.Admin;
import com.madoka.pojo.AdminExample;
import com.madoka.service.AdminService;
import com.madoka.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {
        AdminExample example = new AdminExample();
        example.createCriteria().andANameEqualTo(name);
        List<Admin> admins = adminMapper.selectByExample(example);
        if(admins.size() > 0) {
            Admin admin = admins.get(0);
            String miPwd = MD5Util.getMD5(pwd);
            if(admin.getaPass().equals(miPwd)) {
                return admin;
            }
        }
        return null;
    }
}

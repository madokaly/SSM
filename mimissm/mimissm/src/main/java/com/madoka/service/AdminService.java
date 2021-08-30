package com.madoka.service;

import com.madoka.pojo.Admin;

public interface AdminService {

    Admin login(String name, String pwd);
}

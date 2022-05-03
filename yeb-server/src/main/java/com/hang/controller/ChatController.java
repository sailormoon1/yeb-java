package com.hang.controller;

import com.hang.pojo.Admin;
import com.hang.service.IAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @USER: zzh-
 * @DATE: 2022/4/28
 * @TIME: 17:27
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private IAdminService adminService;

    @ApiOperation("获取所有操作员")
    @GetMapping("/admin")
    public List<Admin> getAllAdmins(String keywords) {
        return adminService.getAllAdmins(keywords);
    }
}

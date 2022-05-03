package com.hang.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.hang.config.utils.FastDFSUtils;
import com.hang.pojo.Admin;
import com.hang.pojo.RespBean;
import com.hang.service.IAdminService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @USER: zzh-
 * @DATE: 2022/4/28
 * @TIME: 19:42
 */
@RestController
public class AdminInfoController {
    @Autowired
    private IAdminService adminService;

    @ApiOperation("更新当前用户信息")
    @PutMapping("/admin/info")
    public RespBean updateAdmin(@RequestBody Admin admin, Authentication authentication) {
        if (adminService.updateById(admin)) {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(admin, null, authentication.getAuthorities())
            );
            return RespBean.success("更新成功!");
        }
        return RespBean.error("更新失败!");
    }


    @ApiOperation("更新用户密码")
    @PutMapping("/admin/pass")
    public RespBean updateAdminPassword(@RequestBody Map<String, Object> info) {
        String oldPass = (String) info.get("oldPass");
        String pass = (String) info.get("pass");
        Integer adminId = (Integer) info.get("adminId");
        return adminService.updateAdminPassword(oldPass, pass, adminId);

    }

    @ApiOperation("更新用户头像")
    @PostMapping("/admin/userface")
    public RespBean updateAdminUserFace(MultipartFile file, Integer id, Authentication authentication) {
        String[] filePath = FastDFSUtils.upload(file);
        String url = FastDFSUtils.getTrackerUrl() + filePath[0] + "/" + filePath[1];
        return adminService.updateAdminUserFace(url, id, authentication);
    }


}

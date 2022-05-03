package com.hang.config.utils;

import com.hang.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @USER: zzh-
 * @DATE: 2022/4/20
 * @TIME: 20:34
 */
public class AdminUtils {
    /**
     * 获取当前登录的操作员
     * @return
     */
    public static Admin getCurrentAdmin() {
        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

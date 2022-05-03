package com.hang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hang.pojo.Department;
import com.hang.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 * @since 2022-04-13
 */
public interface IDepartmentService extends IService<Department> {

    /**
     * 获取所有部门
     * @return
     */
    List<Department> getAllDepartments();

    /**
     * 添加部门
     * @return
     */
    RespBean addDep(Department dep);
    /**
     * 删除部门
     * @return
     */
    RespBean deleteDep(Integer id);



}

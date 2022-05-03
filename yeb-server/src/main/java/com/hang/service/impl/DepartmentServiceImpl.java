package com.hang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hang.mapper.DepartmentMapper;
import com.hang.pojo.Department;
import com.hang.pojo.RespBean;
import com.hang.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2022-04-13
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 获取所有部门
     * @return
     */
    @Override
    public List<Department> getAllDepartments() {

        return departmentMapper.getAllDepartments(-1);
    }


    /**
     * 添加部门
     * @return
     */
    @Override
    public RespBean addDep(Department dep) {
        dep.setEnabled(true);
        departmentMapper.addDep(dep);
        if (1 == dep.getResult()) {
            return RespBean.success("添加成功!",dep);
        }
        return RespBean.error("添加失败!");
    }

    /**
     * 删除部门
     * @return
     */
    @Override
    public RespBean deleteDep(Integer id) {
        Department department = new Department();
        department.setId(id);
        departmentMapper.deleteDep(department);
        if (-2 == department.getResult()) {
            return RespBean.error("该部门下还有子部门,删除失败!");
        }
        if (-1 == department.getResult()) {
            return RespBean.error("该部门下还有员工,删除失败!");
        }
        if (1 == department.getResult()) {
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
}

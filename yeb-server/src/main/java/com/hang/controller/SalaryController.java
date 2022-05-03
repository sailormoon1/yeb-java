package com.hang.controller;


import com.hang.pojo.RespBean;
import com.hang.pojo.Salary;
import com.hang.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhoubin
 * @since 2022-04-13
 */
@RestController
@RequestMapping("/salary/sob")
public class SalaryController {
    @Autowired
    private ISalaryService salaryService;

    @ApiOperation("获取所有工资账套")
    @GetMapping("/")
    public List<Salary> getAllSalaries() {
        return salaryService.list();
    }

    @ApiOperation("添加工资账套")
    @PostMapping("/")
    public RespBean addSalary(@RequestBody Salary salary) {
        salary.setCreateDate(LocalDateTime.now());
        if (salaryService.save(salary)) {
            return RespBean.success("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @ApiOperation("删除工资账套")
    @DeleteMapping("/{id}")
    public RespBean deleteById(@PathVariable Integer id) {
        if (salaryService.removeById(id)) {
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation("更新工资账套")
    @PutMapping("/")
    public RespBean updateSalary(@RequestBody Salary salary) {
        if (salaryService.updateById(salary)) {
            return RespBean.success("更新成功!");
        }
        return RespBean.error("更新失败!");
    }


}

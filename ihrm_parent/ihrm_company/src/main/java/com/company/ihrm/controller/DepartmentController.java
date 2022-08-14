package com.company.ihrm.controller;

import com.company.ihrm.service.CompanyService;
import com.company.ihrm.service.DepartmentService;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.doman.company.Company;
import com.ihrm.doman.company.Department;
import com.ihrm.doman.company.response.DeptListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/company")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/department",method = RequestMethod.POST)
    public Result save(@RequestBody Department department) {
        // 1.设置保存的企业id
        String companyId = "1";
        department.setCompanyId(companyId);
        departmentService.save(department);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/department",method = RequestMethod.GET)
    public Result findAll(Department department){
        //指定企业id查询
        String companyId="1";
        department.setCompanyId(companyId);
        List<Department> departments = departmentService.findAll(department);
        Company company = companyService.findById(companyId);
        DeptListResult deptListResult = new DeptListResult(company,departments);
        return new Result(ResultCode.SUCCESS,deptListResult);
    }


}

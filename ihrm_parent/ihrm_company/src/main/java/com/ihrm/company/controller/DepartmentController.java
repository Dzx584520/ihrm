package com.ihrm.company.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.company.service.CompanyService;
import com.ihrm.company.service.DepartmentService;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.doman.company.Company;
import com.ihrm.doman.company.Department;
import com.ihrm.doman.company.response.DeptListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/company")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CompanyService companyService;

    /**
     * 新增部门
     * @param department
     * @return
     */
    @RequestMapping(value = "/department",method = RequestMethod.POST)
    public Result save(@RequestBody Department department) {
        // 1.设置保存的企业id
        department.setCompanyId(companyId);
        departmentService.save(department);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询部门列表根据企业id
     * @param department
     * @return
     */
    @RequestMapping(value = "/department",method = RequestMethod.GET)
    public Result findAll(Department department){
        //指定企业id查询
        department.setCompanyId(companyId);
        List<Department> departments = departmentService.findAll(department);
        Company company = companyService.findById(companyId);
        DeptListResult deptListResult = new DeptListResult(company,departments);
        return new Result(ResultCode.SUCCESS,deptListResult);
    }

    /**
     * 根据id查询部门
     * @param id
     * @return
     */
    @RequestMapping(value = "/department/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id")String id){
        Department department = departmentService.findById(id);
        return new Result(ResultCode.SUCCESS,department);
    }

    /**
     * 修改部门
     * @param id
     * @param department
     * @return
     */
    @RequestMapping(value = "/department/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id")String id,@RequestBody Department department){
        department.setId(id);
        departmentService.update(department);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @RequestMapping(value = "/department/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable(value = "id")String id){
        departmentService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
}

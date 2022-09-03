package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.doman.system.Role;
import com.ihrm.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/sys")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 添加角色
     */
    @RequestMapping(value = "/role",method = RequestMethod.POST)
    public Result save(@RequestBody Role role){
        role.setCompanyId(companyId);
        roleService.save(role);
        return Result.SUCCESS();
    }

    /**
     * 修改角色
     */
    @RequestMapping(value = "/role/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(name = "id") String id, @RequestBody Role role){
        roleService.update(role);
        return Result.SUCCESS();
    }


    /**
     * 分页查询角色
     */
    @RequestMapping(value = "/role",method = RequestMethod.GET)
    public Result findAll(int page,int size,Role role){
        Page<Role> rolePage = roleService.findSearch(companyId, size, page);
        PageResult<Role> pageResult = new PageResult(rolePage.getTotalElements(), rolePage.getContent());
        return new Result(ResultCode.SUCCESS,pageResult);
    }

    /**
     * 删除角色
     */
    @RequestMapping(value = "/role/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable("id")String id){
        roleService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }


    /**
     * 根据角色id查询角色
     */
    @RequestMapping(value = "/role/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id) {
        Role role = roleService.findById(id);
        return new Result(ResultCode.SUCCESS,role);
    }


}

package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.doman.system.Role;
import com.ihrm.doman.system.response.RoleResult;
import com.ihrm.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


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
        RoleResult roleResult = new RoleResult(role);
        return new Result(ResultCode.SUCCESS,roleResult);
    }

    /**
     * 分配角色权限
     * @return
     */
    @RequestMapping(value = "/role/assignPrem",method = RequestMethod.PUT)
    public Result assignRoles(@RequestBody Map<String,Object> map){
        // 获取被分配用户id
        String roleId = (String) map.get("roleId");
        List<String> ids = (List<String>) map.get("ids");
        roleService.assignRoles(roleId,ids);
        // 2.获取到角色的id列表
        return new Result(ResultCode.SUCCESS);
    }
    @RequestMapping(value="/role/list" ,method=RequestMethod.GET)
    public Result findAll() throws Exception {
        List<Role> roleList = roleService.findAll(companyId);
        return new Result(ResultCode.SUCCESS,roleList);
    }
}

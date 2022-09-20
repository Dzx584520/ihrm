package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.doman.system.Permission;
import com.ihrm.doman.system.User;
import com.ihrm.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author DZX
 * @CrossOrigin 解决跨域问题
 */
@CrossOrigin
@RestController
@RequestMapping("/sys")
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService permissionService;


    /**
     * 添加权限
     */
    @RequestMapping(value = "/permission", method = RequestMethod.POST)
    public Result add(@RequestBody Map<String,Object> map) throws Exception {
        //设置企业id
        map.put("companyId",companyId);
        map.put("enVisible",(int)map.get("enVisible"));
        permissionService.save(map);
        return Result.SUCCESS();
    }

    /**
     * 根据id更新权限信息
     */
    @RequestMapping(value = "/permission/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(name = "id") String id, @RequestBody Map<String,Object> map) throws Exception {
        map.put("id",id);
        permissionService.update(map);
        return Result.SUCCESS();
    }

    /**
     * 根据id删除权限信息
     */
    @RequestMapping(value = "/permission/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(name = "id") String id) throws Exception {
        permissionService.deleteById(id);
        return Result.SUCCESS();
    }

    /**
     * 根据ID获取权限信息
     */
    @RequestMapping(value = "/permission/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(name = "id") String id) throws Exception {
        Map map = permissionService.findById(id);
        return new Result(ResultCode.SUCCESS,map);
    }

    /**
     * 查询权限列表根据企业id
     * @param map
     * @return
     */
    @RequestMapping(value = "/permission",method = RequestMethod.GET)
    public Result findAll(@RequestParam Map map){
        //指定企业id查询
        map.put("companyId",companyId);
        List list = permissionService.findAll(map);
        return new Result(ResultCode.SUCCESS,list);
    }



}

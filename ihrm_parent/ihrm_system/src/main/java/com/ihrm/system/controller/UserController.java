package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.doman.company.Company;
import com.ihrm.doman.company.response.DeptListResult;
import com.ihrm.doman.system.User;
import com.ihrm.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/sys")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    /**
     * 新增用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public Result save(@RequestBody User user) {
        // 1.设置保存的企业id
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        user.setCreateTime(new Date());
        userService.save(user);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询用户列表根据企业id
     * @param page
     * @param size
     * @param map
     * @return
     */
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public Result findAll(int page, int size, @RequestParam Map map){
        //指定企业id查询
        map.put("companyId",companyId);
        Page<User> users = userService.findAll(map, page, size);
        PageResult<User> pages = new PageResult<>(users.getTotalElements(),users.getContent());
        return new Result(ResultCode.SUCCESS,pages);
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id")String id){
        User user = userService.findById(id);
        return new Result(ResultCode.SUCCESS,user);
    }

    /**
     * 修改用户
     * @param id
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id")String id,@RequestBody User user){
        user.setId(id);
        userService.update(user);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable(value = "id")String id){
        userService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
}

package com.ihrm.system.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.utils.JwtUtil;
import com.ihrm.company.service.CompanyService;
import com.ihrm.doman.company.Company;
import com.ihrm.doman.company.response.DeptListResult;
import com.ihrm.doman.system.User;
import com.ihrm.doman.system.response.UserResult;
import com.ihrm.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.HashMap;
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

    @Autowired
    private JwtUtil jwtUtil;

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
        UserResult result = new UserResult(user);
        return new Result(ResultCode.SUCCESS,result);
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

    /**
     * 修改用户状态
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/updateStatusById/{id}",method = RequestMethod.POST)
    public Result updateStatus(@PathVariable(value = "id")String id){
        userService.updateStatusById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 分配用户角色
     * @return
     */
    @RequestMapping(value = "/user/assignRoles",method = RequestMethod.PUT)
    public Result assignRoles(@RequestBody Map<String,Object> map){
        // 获取被分配用户id
        String id = (String) map.get("id");
        List<String> ids = (List<String>) map.get("roleIds");
        userService.assignRoles(id,ids);
        // 2.获取到角色的id列表
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(@RequestBody Map<String,String> map){
        User user = userService.findByMobile(map.get("mobile"));
        if(user == null || !user.getPassword().equals(map.get("password"))){
            return new Result(ResultCode.MOBILEORPASSWORDERROR);
        }else{
            Map<String,Object> map1 = new HashMap<>();
            map1.put("companyId",user.getCompanyId());
            map1.put("companyName",user.getCompanyName());
            jwtUtil.createJWT(user.getId(), user.getUsername(),map1);
        }

        return new Result();
    }
}

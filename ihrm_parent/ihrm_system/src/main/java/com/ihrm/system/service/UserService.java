package com.ihrm.system.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.doman.system.Role;
import com.ihrm.doman.system.User;
import com.ihrm.system.dao.RoleDao;
import com.ihrm.system.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class UserService extends BaseService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RoleDao roleDao;


    //1.保存用户
    public void save(User user) {
        String id = idWorker.nextId()+"";
        user.setId(id);
        user.setPassword("111111");
        user.setEnableState(1);
        userDao.save(user);
    }

    //2.更新用户
    public void update(User user){
        User userInfo = userDao.getOne(user.getId());
        userDao.save(userInfo);
    }

    //3.根据id查询
    public User findById(String id){
        return userDao.findById(id).get();
    }

    //4.查询全部用户列表

    /**
     * 查询参数
     * hasDept：是否分配部门
     * departmentId： 部门id
     * companyId: 企业id
     * @param map
     * @return
     */
    public Page<User> findAll(Map<String, Object> map, int page,int size){
        //1.需要查询条件
        // 构造查询条件
        Specification<User> spec = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                //1. 企业id构造
                if (!StringUtils.isEmpty(map.get("companyId"))) {
                    list.add(criteriaBuilder.equal(root.get("companyId").as(String.class), (String) map.get("companyId")));
                }
                //2.根据部门id查询
                if (!StringUtils.isEmpty(map.get("departmentId"))) {
                    list.add(criteriaBuilder.equal(root.get("departmentId").as(String.class), (String) map.get("departmentId")));
                }
                //3.hasDept 是否分配部门

                if (StringUtils.isEmpty(map.get("hasDept"))){
                    // 未分配
                    if ("0".equals(map.get("hasDept"))) {
                        list.add(criteriaBuilder.isNull(root.get("departmentId")));
                    } else {
                        list.add(criteriaBuilder.isNotNull(root.get("departmentId")));
                    }
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };

        // 2.分页
        Page<User> users = userDao.findAll(spec, new PageRequest(page-1, size));
        return users;
    }


    //5.根据id删除部门
    public void deleteById(String id){
        userDao.deleteById(id);
    }

    /**
     * 修改用户状态
     * @param id
     */
    public void updateStatusById(String id) {
        User user = userDao.findById(id).get();

        if(!StringUtils.isEmpty(user.getEnableState())){
            if(user.getEnableState() == 1){
                user.setEnableState(0);
            } else {
                user.setEnableState(1);
            }
        }
        userDao.save(user);
    }

    /**
     * 分配角色
     */
    public void assignRoles(String userId, List<String> roleIds) {
        User user = userDao.findById(userId).get();
        Set<Role> roles = new HashSet<>();
        for (String roleId : roleIds) {
            Role role = roleDao.findById(roleId).get();
            roles.add(role);
        }
        // 设置用户和角色的关系
        user.setRoles(roles);
        userDao.save(user);
    }
}

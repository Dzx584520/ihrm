package com.ihrm.system.service;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.doman.system.Role;
import com.ihrm.system.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RoleDao roleDao;


    /**
     * 1.添加角色
     */
    public void  save(Role role){
        role.setId(idWorker.nextId() + "");
        roleDao.save(role);
    }

    /**
     * 2.更新角色
     */
    public void update(Role role){
        Role r = roleDao.findById(role.getId()).get();
        r.setName(role.getName());
        r.setDescription(role.getDescription());
        //设置企业id
        r.setCompanyId(r.getCompanyId());
        roleDao.save(r);
    }

    /**
     * 3.根据id查询角色
     */
    public Role findById(String id){
        return  roleDao.findById(id).get();
    }

    /**
     * 4.删除角色
     */
    public void delete(String id){
        roleDao.deleteById(id);
    }

    /**
     * 分页查询角色
     */
    public Page<Role> findSearch(String companyId,int size,int page){
        Specification<Role> specification = new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("companyId").as(String.class), companyId);
            }
        };
        return  roleDao.findAll(specification, PageRequest.of(page-1, size));
    }
}
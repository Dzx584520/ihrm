package com.ihrm.system.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.PermissionConstants;
import com.ihrm.doman.system.Permission;
import com.ihrm.doman.system.PermissionApi;
import com.ihrm.doman.system.Role;
import com.ihrm.system.dao.PermissionApiDao;
import com.ihrm.system.dao.PermissionDao;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService extends BaseService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private PermissionApiDao permissionApiDao;


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

    /**
     * 分配权限
     * @param roleId
     * @param ids
     */
    public void assignRoles(String roleId, List<String> ids) {
        Role role = roleDao.findById(roleId).get();
        Set<Permission> permis = new HashSet<>();
        for (String id : ids) {
            Permission permission = permissionDao.findById(id).get();
            List<Permission> apiList = permissionDao.findByTypeAndPid(PermissionConstants.PY_API, permission.getId());
            permis.addAll(apiList);
            permis.add(permission);
        }
        role.setPermissions(permis);
        roleDao.save(role);
    }
    public List<Role> findAll(String companyId) {
        return roleDao.findAll(getSpec(companyId));
    }
}

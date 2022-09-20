package com.ihrm.system.service;

import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.BeanMapUtils;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.PermissionConstants;
import com.ihrm.doman.system.Permission;
import com.ihrm.doman.system.PermissionApi;
import com.ihrm.doman.system.PermissionMenu;
import com.ihrm.doman.system.PermissionPoint;
import com.ihrm.system.dao.PermissionApiDao;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.PermissionMenuDao;
import com.ihrm.system.dao.PermissionPointDao;
import org.springframework.beans.BeanUtils;
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
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PermissionService extends BaseService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private PermissionMenuDao permissionMenuDao;

    @Autowired
    private PermissionApiDao permissionApiDao;

    @Autowired
    private PermissionPointDao permissionPointDao;

    @Autowired
    private IdWorker idWorker;


    //1.保存用户
    public void save(Map<String,Object> map) throws Exception {
        String id = idWorker.nextId()+"";
        //1.通过map去构造权限对象
        Permission permission = BeanMapUtils.mapToBean(map, Permission.class);
        permission.setId(id);
        Integer type = permission.getType();
        //保存资源
        switch (type) {
            case PermissionConstants.PY_MENU:
                PermissionMenu permissionMenu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                permissionMenu.setId(id);
                permissionMenuDao.save(permissionMenu);
                break;
            case PermissionConstants.PY_POINT:
                PermissionPoint permissionPoint = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                permissionPoint.setId(id);
                permissionPointDao.save(permissionPoint);
                break;
            case PermissionConstants.PY_API:
                PermissionApi permissionApi = BeanMapUtils.mapToBean(map, PermissionApi.class);
                permissionApi.setId(id);
                permissionApiDao.save(permissionApi);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        // 保存权限
        permissionDao.save(permission);
    }

    //2.更新用户
    public void update(Map<String,Object> map) throws Exception {
        //1.通过map去构造权限对象
        Permission permission = BeanMapUtils.mapToBean(map, Permission.class);
        // 查询权限信息
        Permission per = permissionDao.findById(permission.getId()).get();
        per.setName(permission.getName());
        per.setCode(permission.getCode());
        per.setDescription(permission.getDescription());
        per.setEnVisible(permission.getEnVisible());
        Integer type = permission.getType();
        //保存资源
        switch (type) {
            case PermissionConstants.PY_MENU:
                PermissionMenu permissionMenu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                permissionMenu.setId(permission.getId());
                permissionMenuDao.save(permissionMenu);
                break;
            case PermissionConstants.PY_POINT:
                PermissionPoint permissionPoint = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                permissionPoint.setId(permission.getId());
                permissionPointDao.save(permissionPoint);
                break;
            case PermissionConstants.PY_API:
                PermissionApi permissionApi = BeanMapUtils.mapToBean(map, PermissionApi.class);
                permissionApi.setId(permission.getId());
                permissionApiDao.save(permissionApi);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        // 保存权限
        permissionDao.save(per);
    }

    /**
     *3.根据id查询
     * 1.查询权限
     */
    public Map findById(String id) throws CommonException {
        Permission permission = permissionDao.findById(id).get();
        int type = permission.getType();
        Object object = null;
        if (type == PermissionConstants.PY_MENU){
            object = permissionMenuDao.findById(id).get();
        }else if (type == PermissionConstants.PY_POINT) {
            object = permissionPointDao.findById(id).get();
        } else if (type == PermissionConstants.PY_API){
            object = permissionApiDao.findById(id).get();
        } else {
            throw new CommonException(ResultCode.FAIL);
        }
        Map<String, Object> map = BeanMapUtils.beanToMap(object);
        map.put("name",permission.getName());
        map.put("type",permission.getType());
        map.put("code",permission.getCode());
        map.put("description",permission.getDescription());
        map.put("pid",permission.getPid());
        map.put("enVisible",permission.getEnVisible());

        return map;
    }

    //4.查询全部用户列表
    /**
     * 查询全部权限列表
     * @param map
     * @return
     * type：0：菜单 + 按钮（权限点） 1：菜单2：按钮（权限点）3：API接口
     * enVisible :是否查询全部权限 0：查询全部，1：只查询企业所属权限
     */
    public List<Permission> findAll(Map<String, Object> map){
        //1.需要查询条件
        // 构造查询条件
        Specification<Permission> spec = new Specification<Permission>() {
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                //1. 根据父id查询
                if (!StringUtils.isEmpty(map.get("pid"))) {
                    list.add(criteriaBuilder.equal(root.get("pid").as(String.class), (String) map.get("pid")));
                }
                //2.根据enVisible查询
                if (!StringUtils.isEmpty(map.get("enVisible"))) {
                    list.add(criteriaBuilder.equal(root.get("enVisible").as(String.class), (String) map.get("enVisible")));
                }
                //3.根据type查询

                if (StringUtils.isEmpty(map.get("type"))){
                    String ty = (String) map.get("type");
                    CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("type"));
                    // 未分配
                    if ("0".equals(ty)) {
                        in.value(1).value(2);
                    } else {
                        in.value(Integer.valueOf(ty));
                    }
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        List<Permission> list = permissionDao.findAll(spec);
        return list;
    }


    //5.根据id删除部门
    public void deleteById(String id) throws CommonException {
        Permission permission = permissionDao.findById(id).get();
        permissionDao.delete(permission);
        Integer type = permission.getType();
        //保存资源
        switch (type) {
            case PermissionConstants.PY_MENU:
                permissionMenuDao.deleteById(permission.getId());
                break;
            case PermissionConstants.PY_POINT:
                permissionPointDao.deleteById(permission.getId());
                break;
            case PermissionConstants.PY_API:
                permissionApiDao.deleteById(permission.getId());
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
    }
}

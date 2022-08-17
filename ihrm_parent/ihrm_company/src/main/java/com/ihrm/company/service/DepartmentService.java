package com.ihrm.company.service;

import com.ihrm.common.service.BaseService;
import com.ihrm.company.dao.DepartmentDao;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.doman.company.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class DepartmentService extends BaseService {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private IdWorker idWorker;

    //1.保存部门
    public void save(Department department) {
        String id = idWorker.nextId()+"";
        department.setId(id);
        departmentDao.save(department);
    }

    //2.更新部门
    public void update(Department department){
        Department depart = departmentDao.getOne(department.getId());
        depart.setCompanyId(department.getCompanyId());
        depart.setParentId(department.getParentId());
        depart.setName(department.getName());
        depart.setCode(department.getCode());
        depart.setManager(department.getManager());
        depart.setIntroduce(department.getIntroduce());
        departmentDao.save(depart);

    }

    //3.根据id查询
    public Department findById(String id){
        return departmentDao.findById(id).get();
    }

    //4.查询全部部门列表
    public List<Department> findAll(Department department){
        // 用于构造函数
        List<Department> departmentAll = departmentDao.findAll(getSpec(department.getCompanyId()));
        return departmentAll;
    }


    //5.根据id删除部门
    public void deleteById(String id){
        departmentDao.deleteById(id);
    }

}

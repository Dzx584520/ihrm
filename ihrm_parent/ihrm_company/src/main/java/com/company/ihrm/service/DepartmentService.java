package com.company.ihrm.service;

import com.company.ihrm.dao.DepartmentDao;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.doman.company.Company;
import com.ihrm.doman.company.Department;
import com.ihrm.doman.company.response.DeptListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

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
        depart.setPid(department.getPid());
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
        Specification<Department> specification = new Specification<Department>() {
            /*
                root:包含了所有的对象数据
                cq:一般不用
                cb:用来构造查询条件
             */
            @Override
            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate companyId = cb.equal(root.get("companyId").as(String.class), department.getCompanyId());
                return companyId;
            }
        };
        List<Department> departmentAll = departmentDao.findAll(specification);
        return departmentAll;
    }


    //5.根据id删除部门
    public void deleteById(String id){
        departmentDao.deleteById(id);
    }

}

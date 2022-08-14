package com.company.ihrm.dao;

import com.ihrm.doman.company.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DepartmentDao extends JpaRepository<Department, String> , JpaSpecificationExecutor<Department> {
}

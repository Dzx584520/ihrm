package com.company.ihrm.dao;

import com.company.ihrm.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompanyDao   extends JpaRepository<Company, String>, JpaSpecificationExecutor<Company> {
}

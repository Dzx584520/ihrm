package com.company.ihrm;

import com.company.ihrm.dao.CompanyDao;
import com.company.ihrm.entity.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CompanyTest {

    @Autowired
    private CompanyDao companyDao;

    @Test
    public void test(){
        Company company = companyDao.findById("1").get();
        System.out.println(company);
    }

}

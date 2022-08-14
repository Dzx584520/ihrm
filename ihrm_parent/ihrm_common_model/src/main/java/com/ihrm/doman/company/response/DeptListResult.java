package com.ihrm.doman.company.response;

import com.ihrm.doman.company.Company;
import com.ihrm.doman.company.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DeptListResult {
    private String companyId;
    private String  companyName;
    private String companyManage;
    private List<Department> deptList;

    public DeptListResult(Company company,List deptList){
        this.companyId = company.getId();
        this.companyName = company.getName();
        this.companyManage = company.getLegalRepresentative();//公司联系人
        this.deptList = deptList;
    }

}

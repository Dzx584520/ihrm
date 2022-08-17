package com.ihrm.common.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BaseService<T> {
    public Specification<T> getSpec(String companyId){
            Specification<T> specification = new Specification<T>() {
                @Override
                public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                    Predicate predicate = cb.equal(root.get("companyId").as(String.class), companyId);
                    return predicate;
                }
            };
        return specification;
    }

}

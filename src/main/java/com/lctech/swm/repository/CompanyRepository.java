package com.lctech.swm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.lctech.swm.domain.Agent;
import com.lctech.swm.domain.Company;

public interface CompanyRepository extends CrudRepository<Company, Long> {

	List<Company> findAll();
}

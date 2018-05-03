package com.lctech.swm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lctech.swm.config.Constants;
import com.lctech.swm.domain.Account;
import com.lctech.swm.domain.Company;
import com.lctech.swm.repository.CompanyRepository;

@Service
public class CompanyService {
	@Autowired
	CompanyRepository companyRepository;

	
	public List<Company> findByAccount(Account acc){
		List<Company> lsp = new ArrayList<>();
		if(acc.getLevel() == Constants.LEVEL_TYPE_ADMIN) {
			lsp = companyRepository.findAll();
		} else if(acc.getLevel() == Constants.LEVEL_TYPE_COMPANY){
			lsp.add(acc.getCompany());
		}
		return lsp;
	}
	
	public Company findById(Long id) {
		return companyRepository.findOne(id);
	}

	/**
	 * @param company
	 */
	public void save(Company company) {
		companyRepository.save(company);
	}

	/**
	 * @param id
	 */
	public void delete(Long id) {
		companyRepository.delete(id);
	}

	public List<Company> findAll(){
		return companyRepository.findAll();
	}
}

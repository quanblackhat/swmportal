package com.lctech.swm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lctech.swm.domain.Contract;
import com.lctech.swm.repository.ContractRepository;

@Service
public class ContractService {
	@Autowired
	ContractRepository contractRepository;

	public Page<Contract> findAll(Pageable pageable) {
		return contractRepository.findAll(pageable);
	}

	public Page<Contract> load(String name, Long provinceId, Long districtId,
			Long regionId, Byte status, Pageable pageable) {
		return contractRepository.load(name, provinceId, districtId, regionId,
				status, pageable);
	}

	public List<Contract> findAll() {
		return (List<Contract>) contractRepository.findAll();
	}

	public Contract findById(Long id) {
		return contractRepository.findOne(id);
	}

	/**
	 * @param device
	 */
	public void save(Contract contract) {
		contractRepository.save(contract);
	}

	/**
	 * @param id
	 */
	public void delete(Long id) {
		contractRepository.delete(id);
	}

	/**
	 * @param id
	 */
	public void delete(List<Long> ids) {
		contractRepository.deleteLst(ids);
	}


	/**
	 * Find all contract number of user.
	 *
	 * @param userId id of user.
	 * @return list of contract number.
	 */
	public List<Contract> findContractNumbersByUserid(Long userId){
		return contractRepository.findContractNumbersByUserid(userId);
	}
}

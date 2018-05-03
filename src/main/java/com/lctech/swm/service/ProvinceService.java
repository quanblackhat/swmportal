package com.lctech.swm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lctech.swm.domain.Province;
import com.lctech.swm.repository.ProvinceRepository;

@Service
public class ProvinceService {
	@Autowired
	ProvinceRepository provinceRepository;

	public List<Province> findAll(){
		return (List<Province>) provinceRepository.findAll();
	}
}

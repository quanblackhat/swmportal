package com.lctech.swm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lctech.swm.domain.District;
import com.lctech.swm.repository.DistrictRepository;

@Service
public class DistrictService {

	@Autowired
	DistrictRepository districtRepository;

	public List<District> findByProvince(Long provinceId) {
		return (List<District>) districtRepository.findByProvinceId(provinceId);
	}
}

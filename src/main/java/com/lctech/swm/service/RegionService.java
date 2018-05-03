package com.lctech.swm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lctech.swm.domain.Region;
import com.lctech.swm.repository.RegionRepository;

@Service
public class RegionService {

	@Autowired
	RegionRepository regionRepository;

	public List<Region> findByRegion(Long districtId) {
		return (List<Region>) regionRepository.findByDistrictId(districtId);
	}
}

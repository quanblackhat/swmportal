package com.lctech.swm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lctech.swm.domain.MeasureTransaction;
import com.lctech.swm.repository.MeasureTransRepository;

@Service
public class MeasureTransService {
	@Autowired
	MeasureTransRepository measureTransRepository;

	public Page<MeasureTransaction> findAll(Pageable pageable) {
		return measureTransRepository.findAll(pageable);
	}

//	public Page<MeasureTransaction> load(String name, String code,
//			Long provinceId, Long districtId, Long regionId, String startDate,
//			String endDate, Pageable pageable) {
//		return measureRepository.load(name, code, provinceId, districtId,
//				regionId, startDate, endDate, pageable);
//	}
	public List<MeasureTransaction> findByCodeAndDate(String code,Date startdate,Date enddate) {
		return measureTransRepository.findByCodeAndDate(code, startdate,enddate);
	}
	public MeasureTransaction findById(Long id) {
		return measureTransRepository.findOne(id);
	}

}

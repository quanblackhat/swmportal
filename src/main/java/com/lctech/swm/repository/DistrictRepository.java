package com.lctech.swm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lctech.swm.domain.District;

public interface DistrictRepository extends CrudRepository<District, Long> {

	@Query(value = "From District Where province_id = ?1")
	List<District> findByProvinceId(Long provinceId);

}

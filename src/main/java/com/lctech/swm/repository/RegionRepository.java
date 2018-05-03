package com.lctech.swm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lctech.swm.domain.Region;

public interface RegionRepository extends CrudRepository<Region, Long> {
	
	@Query(value = "From Region Where district_id = ?1")
	List<Region> findByDistrictId(Long districtId);
}

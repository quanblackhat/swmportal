package com.lctech.swm.repository;

import java.util.List;

import com.lctech.swm.domain.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lctech.swm.domain.Contract;

public interface ContractRepository extends CrudRepository<Contract, Long> {
	
	Page<Contract> findAll(Pageable pageable);
	
	@Query(value = "Select c From Contract c" 
			+ " Left Join c.user u"
			+ " Left Join u.region r"
			+ " Left Join r.district t"
			+ " Left Join t.province p"
			+ " Where (u.name Like concat('%',?1,'%') or ?1 = '')"
			+ " And (p.id = ?2 or ?2 = -1L)"
			+ " And (t.id = ?3 or ?3 = -1L)"
			+ " And (r.id = ?4 or ?4 = -1L)"
			+ " And (c.status = ?5 or ?5 = -1L)"
			+ " Order by u.name, c.id",
			countQuery = "Select count(c.id) From Contract c"
					+ " Left Join c.user u"
					+ " Left Join u.region r"
					+ " Left Join r.district t"
					+ " Left Join t.province p"
					+ " Where (u.name Like concat('%',?1,'%') or ?1 = '')"
					+ " And (p.id = ?2 or ?2 = -1L)"
					+ " And (t.id = ?3 or ?3 = -1L)"
					+ " And (r.id = ?4 or ?4 = -1L)"
					+ " And (c.status = ?5 or ?5 = -1L)",
			nativeQuery = false)
	Page<Contract> load(String name, Long provinceId, Long districtId,
			Long regionId, Byte status, Pageable pageable);
	
	@Modifying
	@Transactional
	@Query(value = "Delete From contract Where id in (:ids)", nativeQuery = true)
	void deleteLst(@Param("ids") List<Long> ids);
	
	@Query(value = "SELECT c FROM Contract c INNER JOIN c.user u WHERE u.id = :userId")
	List<Contract> findContractNumbersByUserid(@Param("userId") Long userId);
}

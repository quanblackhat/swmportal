package com.lctech.swm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lctech.swm.domain.Agent;

public interface AgentRepository extends CrudRepository<Agent, Long> {
	
	Page<Agent> findAll(Pageable pageable);

	@Query(value = "Select u From Agent u"
			+ " Left Join u.region r"
			+ " Left Join r.district t"
			+ " Left Join t.province p"
			+ " Where (u.agentName Like concat('%',?1,'%') or ?1 = '')"
			+ " And (r.id = ?2 or ?2 = -1L)"
			+ " And (t.id = ?3 or ?3 = -1L)"
			+ " And (p.id = ?4 or ?4 = -1L)",
			countQuery = "Select count(u.id) From Agent u"
					+ " Left Join u.region r"
					+ " Left Join r.district t"
					+ "	Left Join t.province p"
					+ " Where (u.agentName Like concat('%',?1,'%') or ?1 = '')"
					+ " And (r.id = ?2 or ?2 = -1L)"
					+ " And (t.id = ?3 or ?3 = -1L)"
					+ " And (p.id = ?4 or ?4 = -1L)",
			nativeQuery = false)
	Page<Agent> load(String name, Long regionId, Long districtId,
			Long provinceId, Pageable pageable);

	@Modifying
	@Transactional
	@Query(value = "Delete From Agent Where id in (:ids)", nativeQuery = true)
	void deleteLst(@Param("ids") List<Long> ids);

}

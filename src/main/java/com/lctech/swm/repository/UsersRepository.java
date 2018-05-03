package com.lctech.swm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.transaction.annotation.Transactional;

import com.lctech.swm.domain.Agent;
import com.lctech.swm.domain.Users;

public interface UsersRepository extends CrudRepository<Users, Long> {
	
	Page<Users> findAll(Pageable pageable);

	@Query(value = "Select u From Users u"
			+ " Left Join u.region r"
			+ " Left Join r.district t"
			+ " Left Join t.province p"
			+ " Where (u.name Like concat('%',?1,'%') or ?1 = '')"
			+ " And (r.id = ?2 or ?2 = -1L)"
			+ " And (t.id = ?3 or ?3 = -1L)"
			+ " And (p.id = ?4 or ?4 = -1L)",
			countQuery = "Select count(u.id) From Users u"
					+ " Left Join u.region r"
					+ " Left Join r.district t"
					+ "	Left Join t.province p"
					+ " Where (u.name Like concat('%',?1,'%') or ?1 = '')"
					+ " And (r.id = ?2 or ?2 = -1L)"
					+ " And (t.id = ?3 or ?3 = -1L)"
					+ " And (p.id = ?4 or ?4 = -1L)",
			nativeQuery = false)
	Page<Users> load(String name, Long regionId, Long districtId,
			Long provinceId, Pageable pageable);

	@Modifying
	@Transactional
	@Query(value = "Delete From users Where id in (:ids)", nativeQuery = true)
	void deleteLst(@Param("ids") List<Long> ids);

	/**
	 * Get list user who have signed contracts with company.
	 *
	 * @param companyId id of company.
	 * @return list of user.
	 */
	@Query(value = "SELECT u FROM Contract c"
			+ " INNER JOIN c.user u WHERE c.company.id = :companyId")
	List<Users> findByCompany(@Param("companyId") Long companyId);

	List<Users> findAll();
}

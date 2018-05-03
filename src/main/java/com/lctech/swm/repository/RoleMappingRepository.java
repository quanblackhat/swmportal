package com.lctech.swm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.lctech.swm.domain.RoleMapping;

public interface RoleMappingRepository extends CrudRepository<RoleMapping, Long> {

	/**
	 * Find List<RoleMapping> by group role id and status.
	 * 
	 * @param groupRole
	 * @param status
	 * @return
	 */
	List<RoleMapping> findByGroupRoleAndStatus(@Param("groupRole")Long groupRole,@Param("status")Boolean status);
	
	@Query(value = "select nextval ('seq_group_role')", nativeQuery = true)
	Long getnextval();
	
	/**
	 * Get list RoleMapping by group role id
	 * 
	 * @param groupRole
	 * @return
	 */
	List<RoleMapping> findByGroupRole(@Param("groupRole")Long groupRole);
}

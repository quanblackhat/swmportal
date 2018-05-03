package com.lctech.swm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lctech.swm.domain.Device;

public interface DeviceRepository extends CrudRepository<Device, Long>, PagingAndSortingRepository<Device, Long> {

    List<Device> findAll();

	Page<Device> findAll(Pageable pageable);

	@Query(value = "Select d From Device d"
			+ " Left Join d.region r"
			+ " Left Join r.district t"
			+ " Left Join t.province p"
			+ " Where (d.code Like concat('%',?1,'%') or ?1 = '')"
			+ " And (r.id = ?2 or ?2 = -1L)"
			+ " And (t.id = ?3 or ?3 = -1L)"
			+ " And (p.id = ?4 or ?4 = -1L)"
			+ " And d.setupDate = ?5",
			countQuery = "Select count(d.id) From Device d"
					+ " Left Join d.region r"
					+ " Left Join r.district t"
					+ "	Left Join t.province p"
					+ " Where (d.code Like concat('%',?1,'%') or ?1 = '')"
					+ " And (r.id = ?2 or ?2 = -1L)"
					+ " And (t.id = ?3 or ?3 = -1L)"
					+ " And (p.id = ?4 or ?4 = -1L)"
					+ " And d.setupDate = ?5",
			nativeQuery = false)
	Page<Device> load(String code, Long regionId, Long districtId,
			Long provinceId,  Date setupDate, Pageable pageable);

	@Query(value = "Select d From Device d"
			+ " Left Join d.region r"
			+ " Left Join r.district t"
			+ " Left Join t.province p"
			+ " Where (d.code Like concat('%',?1,'%') or ?1 = '')"
			+ " And (r.id = ?2 or ?2 = -1L)"
			+ " And (t.id = ?3 or ?3 = -1L)"
			+ " And (p.id = ?4 or ?4 = -1L)",
			countQuery = "Select count(d.id) From Device d"
					+ " Left Join d.region r"
					+ " Left Join r.district t"
					+ "	Left Join t.province p"
					+ " Where (d.code Like concat('%',?1,'%') or ?1 = '')"
					+ " And (r.id = ?2 or ?2 = -1L)"
					+ " And (t.id = ?3 or ?3 = -1L)"
					+ " And (p.id = ?4 or ?4 = -1L)",
			nativeQuery = false)
	Page<Device> load(String code, Long regionId, Long districtId,
			Long provinceId, Pageable pageable);

	@Modifying
	@Transactional
	@Query(value = "Delete From device Where id in (:ids)", nativeQuery = true)
	void deleteLst(@Param("ids") List<Long> ids);


    List<Device> findByCode(String deviceCode);

    @Query(value = "Select d From Device d Where d.code IN :deviceCodes")
    List<Device> findByCodes(@Param("deviceCodes") List<String> deviceCodes);

}

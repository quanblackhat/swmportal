package com.lctech.swm.repository;

import java.util.Date;
import java.util.List;

import com.lctech.swm.domain.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.lctech.swm.domain.ContractDeviceMapping;

public interface ContractDeviceMappingRepository extends CrudRepository<ContractDeviceMapping, Long> {
	

	@Query(value = "SELECT d.code "
			+ "FROM ContractDeviceMapping cdm "
			+ "INNER JOIN cdm.device d "
			+ "INNER JOIN cdm.contract c "
			+ "WHERE cdm.contract.numberContract = :numberContract")
	List<String> findListDeviceCodesByContractNumber(@Param("numberContract") String numberContract);


    @Query(value= "SELECT DISTINCT device"
            + " FROM ContractDeviceMapping cdm"
            + " INNER JOIN cdm.device device"
            + " INNER JOIN device.measureTransaction m"
            + " INNER JOIN cdm.contract contract"
            + " INNER JOIN contract.user user"
            + " INNER JOIN contract.company company"
            + " LEFT JOIN user.region region"
            + " LEFT JOIN region.district district"
            + " LEFT JOIN district.province province"
            + " WHERE (user.name LIKE CONCAT('%',:name,'%') OR :name = '')"
            + " AND (user.id = :userId OR :userId = -1L)"
            + " AND m.transactionDate >= :startDate"
            + " AND m.transactionDate <= :endDate"
            + " AND (province.id = :provinceId OR :provinceId = -1L)"
            + " AND (district.id = :districtId OR :districtId = -1L)"
            + " AND (region.id = :regionId OR :regionId = -1L)"
            + " AND (company.id = :companyId OR :companyId = -1L)",
            countQuery = "SELECT count(device.id)"
                    + " FROM ContractDeviceMapping cdm"
                    + " INNER JOIN cdm.device device"
                    + " INNER JOIN device.measureTransaction m"
                    + " INNER JOIN cdm.contract contract"
                    + " INNER JOIN contract.user user"
                    + " INNER JOIN contract.company company"
                    + " LEFT JOIN user.region region"
                    + " LEFT JOIN region.district district"
                    + " LEFT JOIN district.province province"
                    + " WHERE (user.name LIKE CONCAT('%',:name,'%') OR :name = '')"
                    + " AND (user.id = :userId OR :userId = -1L)"
                    + " AND m.transactionDate >= :startDate"
                    + " AND m.transactionDate <= :endDate"
                    + " AND (province.id = :provinceId OR :provinceId = -1L)"
                    + " AND (district.id = :districtId OR :districtId = -1L)"
                    + " AND (region.id = :regionId OR :regionId = -1L)"
                    + " AND (company.id = :companyId OR :companyId = -1L)"
                    + " GROUP BY device.id")
    Page<Device> findByDate(@Param("name") String name,
                            @Param("userId") Long userId,
                            @Param("provinceId") Long provinceId,
                            @Param("districtId") Long districtId,
                            @Param("regionId") Long regionId,
                            @Param("companyId") Long companyId,
                            @Param("startDate") Date startDate,
                            @Param("endDate") Date endDate,
                            Pageable pageable);

    @Query(value= "SELECT device"
            + " FROM ContractDeviceMapping cdm"
            + " INNER JOIN cdm.device device"
            + " INNER JOIN device.invoices invoices"
            + " INNER JOIN cdm.contract contract"
            + " INNER JOIN contract.user user"
            + " INNER JOIN contract.company company"
            + " LEFT JOIN user.region region"
            + " LEFT JOIN region.district district"
            + " LEFT JOIN district.province province"
            + " WHERE (user.name LIKE CONCAT('%',:name,'%') OR :name = '')"
            + " AND (user.id = :userId OR :userId = -1L)"
            + " AND (invoices.month = :month)"
            + " AND (invoices.year = :year)"
            + " AND (province.id = :provinceId OR :provinceId = -1L)"
            + " AND (district.id = :districtId OR :districtId = -1L)"
            + " AND (region.id = :regionId OR :regionId = -1L)"
            + " AND (company.id = :companyId OR :companyId = -1L)",
            countQuery = "SELECT count(device.id)"
                    + " FROM ContractDeviceMapping cdm"
                    + " INNER JOIN cdm.device device"
                    + " INNER JOIN device.invoices invoices"
                    + " INNER JOIN cdm.contract contract"
                    + " INNER JOIN contract.user user"
                    + " INNER JOIN contract.company company"
                    + " LEFT JOIN user.region region"
                    + " LEFT JOIN region.district district"
                    + " LEFT JOIN district.province province"
                    + " WHERE (user.name LIKE CONCAT('%',:name,'%') OR :name = '')"
                    + " AND (user.id = :userId OR :userId = -1L)"
                    + " AND (invoices.month = :month)"
                    + " AND (invoices.year = :year)"
                    + " AND (province.id = :provinceId OR :provinceId = -1L)"
                    + " AND (district.id = :districtId OR :districtId = -1L)"
                    + " AND (region.id = :regionId OR :regionId = -1L)"
                    + " AND (company.id = :companyId OR :companyId = -1L)")
    Page<Device> findByMonthAndYear(@Param("name") String name,
                                      @Param("userId") Long userId,
                                      @Param("provinceId") Long provinceId,
                                      @Param("districtId") Long districtId,
                                      @Param("regionId") Long regionId,
                                      @Param("companyId") Long companyId,
                                      @Param("month") Integer month,
                                      @Param("year") Integer year,
                                      Pageable pageable);
}

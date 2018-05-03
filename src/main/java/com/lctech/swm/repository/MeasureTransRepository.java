package com.lctech.swm.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lctech.swm.domain.MeasureTransaction;
import org.springframework.data.repository.query.Param;

public interface MeasureTransRepository
		extends
			CrudRepository<MeasureTransaction, Long> {

	Page<MeasureTransaction> findAll(Pageable pageable);

//	@Query(value = "Select m From MeasureTransaction m" 
//			+ " Left Join m.device d"
//			+ " And (d.code Like concat('%',?1,'%') or ?1 = '')"
//			+ " And (p.id = ?3 or ?3 = -1L)"
//			+ " And (t.id = ?4 or ?4 = -1L)"
//			+ " And (r.id = ?5 or ?5 = -1L)"
//			+ " And m.transactionDate >= to_date(?6, 'dd/MM/yyyy')"
//			+ " And m.transactionDate < to_date(?7, 'dd/MM/yyyy') + 1"
//			+ " Order by m.transactionDate desc, m.id desc",
//			countQuery = "Select count(m.id) From MeasureTransaction m"
//					+ " Left Join m.device d"
//					+ " And (d.code Like concat('%',?2,'%') or ?2 = '')"
//					+ " And (p.id = ?3 or ?3 = -1L)"
//					+ " And (t.id = ?4 or ?4 = -1L)"
//					+ " And (r.id = ?5 or ?5 = -1L)"
//					+ " And m.transactionDate >= to_date(?6, 'dd/MM/yyyy')"
//				    + " And m.transactionDate < to_date(?7, 'dd/MM/yyyy') + 1",
//			nativeQuery = false)
//	Page<MeasureTransaction> load(String code, Long regionId, String startDate, String endDate, Pageable pageable);

	@Query(value = "Select m From MeasureTransaction m"
			+ " Left Join m.device d"
			+ " Where (d.code Like concat('%',?1,'%') or ?1 = '')"
			+ " And m.transactionDate > ?2 And m.transactionDate <= ?3  order by m.transactionDate",
			nativeQuery = false)
	List<MeasureTransaction> findByCodeAndDate(String code,Date startdate,Date enddate);

	/**
	 * Get amount of device
	 * @param deviceCode
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	@Query(value = "Select min(m.amount),max(m.amount) From MeasureTransaction m"
			+ " Left Join m.device d"
			+ " Where (d.code Like concat('%',?1,'%') or ?1 = '')"
			+ " And m.transactionDate > ?2 And m.transactionDate <= ?3  order by m.transactionDate",
			nativeQuery = false)
	List<Long> getAmountByDeviceAndDate(String deviceCode,Date startdate,Date enddate);

	/**
	 *	Gets measurement report used by a device for a period of time.
	 */
	@Query(value = "SELECT m FROM MeasureTransaction m"
			+ " LEFT JOIN m.device d"
			+ " WHERE m.transactionDate = :date AND d.code = :deviceCode ORDER BY m.transactionDate")
	MeasureTransaction getByDeviceAndTransactionDate(@Param("deviceCode") String deviceCode,
                                                     @Param("date") Date date);

	/**
	 *	Gets measurement report used by a device for a period of time.
	 */
	@Query(value = "SELECT m FROM MeasureTransaction m"
			+ " LEFT JOIN m.device d"
			+ " WHERE d.code = :deviceCode AND m.transactionDate >= :startDate AND m.transactionDate <= :endDate"
			+ " ORDER BY m.amount")
	List<MeasureTransaction> getByDeviceAndTransactionDate(@Param("deviceCode") String deviceCode,
                                                           @Param("startDate") Date startDate,
                                                           @Param("endDate") Date endDate);

    /**
     * Gets measurement report used by a device in a month.
     */
    @Query(value = "SELECT m FROM MeasureTransaction m"
            + " INNER JOIN m.device d"
            + " WHERE d.code = :deviceCode"
            + " AND function('extract', month from m.transactionDate) = :month"
            + " AND function('extract', year from m.transactionDate) = :year"
            + " ORDER BY m.amount")
    List<MeasureTransaction> getByMonthAndYear(@Param("deviceCode") String deviceCode,
                                                 @Param("month") Integer month,
                                                 @Param("year") Integer year);

}

package com.lctech.swm.repository;

import com.lctech.swm.domain.Price;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.nio.channels.SelectableChannel;
import java.util.Date;
import java.util.List;

public interface PriceRepository extends CrudRepository<Price, Long> {

    @Query(value = "Select min(m.amount),max(m.amount) From MeasureTransaction m"
            + " Left Join m.device d"
            + " Where (d.code Like concat('%',?1,'%') or ?1 = '')"
            + " And m.transactionDate > ?2 And m.transactionDate <= ?3  order by m.transactionDate",
            nativeQuery = false)
    List<Long> getAmountByDeviceAndDate(String deviceCode,Date startdate,Date enddate);

    @Query(value = "SELECT p FROM Price p WHERE p.startM2 IS NOT NULL ORDER BY p.startM2 ASC")
    List<Price> getPriceOfHousehold();

    @Query(value = "Select p FROM Price p INNER join p.uses u WHERE u.usesCode = :usesCode")
    Price getPriceByUses(@Param("usesCode") String usesCode);


}


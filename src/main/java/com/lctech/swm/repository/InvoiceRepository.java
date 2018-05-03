package com.lctech.swm.repository;

import com.lctech.swm.domain.Invoice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

    List<Invoice> findAll();

    List<Invoice> findByYearAndMonthAndDeviceCodeIn(Integer year, Integer month, List<String> deviceCode);
}

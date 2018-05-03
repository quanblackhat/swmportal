package com.lctech.swm.service;

import com.lctech.swm.domain.Device;
import com.lctech.swm.repository.ContractDeviceMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ContractDeviceMappingService {

	private final ContractDeviceMappingRepository contractDeviceMappingRepository;

    @Autowired
    public ContractDeviceMappingService(ContractDeviceMappingRepository contractDeviceMappingRepository) {
        this.contractDeviceMappingRepository = contractDeviceMappingRepository;
    }

    public Page<Device> findByDate(String name, Long userId, Long provinceId, Long districtId, Long regionId, Long companyId,
                             Date startDate, Date endDate, Pageable pageable){
        return contractDeviceMappingRepository.findByDate(name, userId, provinceId, districtId, regionId, companyId, startDate, endDate, pageable);
    }

    public Page<Device> findByMonthAndYear(String name, Long userId, Long provinceId, Long districtId, Long regionId, Long companyId,
                             Integer month, Integer year, Pageable pageable){
        return contractDeviceMappingRepository.findByMonthAndYear(name, userId, provinceId, districtId, regionId,
                companyId, month, year, pageable);
    }
}

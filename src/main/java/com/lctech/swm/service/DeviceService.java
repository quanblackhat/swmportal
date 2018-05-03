package com.lctech.swm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lctech.swm.domain.Device;
import com.lctech.swm.repository.DeviceRepository;

@Service
public class DeviceService {
	@Autowired
	DeviceRepository deviceRepository;

	public Page<Device> findAll(Pageable pageable) {
		return deviceRepository.findAll(pageable);
	}

	public Page<Device> load(String code, Long regionId, Long districtId,
			Long provinceId, Date setupDate, Pageable pageable) {
		if (setupDate != null) {
			return deviceRepository.load(code, regionId, districtId, provinceId,
					setupDate, pageable);
		}
		return deviceRepository.load(code, regionId, districtId, provinceId,
				pageable);
	}

	public Device findById(Long id) {
		return deviceRepository.findOne(id);
	}

	/**
	 * @param device
	 */
	public void save(Device device) {
		deviceRepository.save(device);
	}

	/**
	 * @param id
	 */
	public void delete(List<Long> ids) {
		deviceRepository.deleteLst(ids);
	}

	public List<Device> findByCode(String deviceCode){
	    return deviceRepository.findByCode(deviceCode);
    }

    public List<Device> findByCodes(List<String> deviceCode){
        return deviceRepository.findByCodes(deviceCode);
    }

}

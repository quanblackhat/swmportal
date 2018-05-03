package com.lctech.swm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lctech.swm.domain.Uses;
import com.lctech.swm.repository.UsesRepository;

@Service
public class UsesService {
	@Autowired
	UsesRepository usesRepository;

	public List<Uses> findAll() {
		return (List<Uses>) usesRepository.findAll();
	}

	public Uses findById(Long id) {
		return usesRepository.findOne(id);
	}

	/**
	 * @param device
	 */
	public void save(Uses uses) {
		usesRepository.save(uses);
	}

	/**
	 * @param id
	 */
	public void delete(Long id) {
		usesRepository.delete(id);
	}
	
}

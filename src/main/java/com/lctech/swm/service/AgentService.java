package com.lctech.swm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lctech.swm.domain.Agent;
import com.lctech.swm.repository.AgentRepository;

@Service
public class AgentService {
	@Autowired
	AgentRepository agentRepository;

	public Page<Agent> findAll(Pageable pageable) {
		return agentRepository.findAll(pageable);
	}

	public Page<Agent> load(String code, Long regionId, Long districtId,
			Long provinceId, Pageable pageable) {
		return agentRepository.load(code, regionId, districtId, provinceId,
				pageable);
	}

	public Agent findById(Long id) {
		return agentRepository.findOne(id);
	}

	/**
	 * @param device
	 */
	public void save(Agent agent) {
		agentRepository.save(agent);
	}

	/**
	 * @param id
	 */
	public void delete(List<Long> ids) {
		agentRepository.deleteLst(ids);
	}

	public List<Agent> findAll() {
		return (List<Agent>) agentRepository.findAll();
	}
}

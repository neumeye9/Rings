package com.krista.rings.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.krista.rings.models.Ring;
import com.krista.rings.repositories.RingRepository;

@Service
public class RingService {
	
	private RingRepository ringRepository;
	
	public RingService(RingRepository ringRepository) {
		this.ringRepository = ringRepository;
	}
	
	public void createRing(Ring ring) {
		ringRepository.save(ring);
	}
	
	public List<Ring> allRings(){
		return ringRepository.findAll();
	}
	
	public Ring findByName(String name) {
		return ringRepository.findByName(name);
	}
	
	public void update(Ring ring) {
		ringRepository.save(ring);
	}
	
	public void destroy(Long id) {
		ringRepository.delete(id);
	}

}

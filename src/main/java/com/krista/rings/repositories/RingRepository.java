package com.krista.rings.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.krista.rings.models.Ring;

@Repository
public interface RingRepository extends CrudRepository<Ring, Long> {
	List<Ring> findAll();
	Ring findByName(String name);
}

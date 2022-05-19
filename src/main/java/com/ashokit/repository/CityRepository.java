package com.ashokit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entity.CityMasterEntity;
import com.ashokit.entity.StateMasterEntity;

public interface CityRepository extends JpaRepository<CityMasterEntity, Integer> {

	//select * from city_master where stateId=?
	public List<CityMasterEntity> findByStateId(Integer stateId);
}

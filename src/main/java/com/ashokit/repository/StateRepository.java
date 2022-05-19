package com.ashokit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entity.StateMasterEntity;

public interface StateRepository extends JpaRepository<StateMasterEntity, Integer> {
	
	//select * from state_master where countryId=?
	public List<StateMasterEntity> findByCountryId(Integer countryId);
}

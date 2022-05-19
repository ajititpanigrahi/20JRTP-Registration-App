package com.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entity.CountryMasterEntity;
import com.ashokit.entity.UserAccountEntity;

public interface CountryRepository extends JpaRepository<CountryMasterEntity, Integer> {
	

}

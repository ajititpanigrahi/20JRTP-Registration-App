package com.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entity.UserAccountEntity;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Integer> {
	
	//select * from user_accounts where user_email=? and user_pwd=?
	public UserAccountEntity findByEmailAndPassword(String email,String pwd);
	
	//select * from user_accounts where user_email=?
	public UserAccountEntity findByEmail(String emailId);

}

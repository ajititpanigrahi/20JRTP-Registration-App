package com.ashokit.service;

import java.util.Map;
import com.ashokit.binding.LoginForm;
import com.ashokit.binding.UnlockAccForm;
import com.ashokit.binding.UserForm;


public interface UserMgmtService {

	String signUp(UserForm user);

	String signIn(LoginForm user);

	String forgotPwd(String emailId);
		
	String accUnlock(UnlockAccForm unlockAccForm);
	
	public String emailCheck(String emailId);

	Map<Integer, String> loadCuntries();

	Map<Integer, String> loadStates(int countryId);

	Map<Integer, String> loadCities(int stateId);

}

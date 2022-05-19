package com.ashokit.serviceimpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.binding.LoginForm;
import com.ashokit.binding.UnlockAccForm;
import com.ashokit.binding.UserForm;
import com.ashokit.entity.CityMasterEntity;
import com.ashokit.entity.CountryMasterEntity;
import com.ashokit.entity.StateMasterEntity;
import com.ashokit.entity.UserAccountEntity;
import com.ashokit.repository.CityRepository;
import com.ashokit.repository.CountryRepository;
import com.ashokit.repository.StateRepository;
import com.ashokit.repository.UserAccountRepository;
import com.ashokit.service.UserMgmtService;
import com.ashokit.util.EmailUtils;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

	@Autowired
	UserAccountRepository userRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	CityRepository cityRepoSitory;
	
	@Autowired
	EmailUtils emailUtils;

	public String signUp(UserForm userForm) {

		UserAccountEntity entity = new UserAccountEntity();

		BeanUtils.copyProperties(userForm, entity);

		entity.setAccStatus("LOCKED");

		entity.setPassword(generateRandomPassword(6));

		userRepository.save(entity);

		String email = userForm.getEmail();
		String subject="User Registration - Ashok IT";
		String fileName = "UNLOCK-ACC-EMAIL-BODY-TEMPLATE.txt";
		String body=readMailBodyContent(fileName, entity);
		boolean isSent = emailUtils.sendEmail(email, subject, body);
		
		if(isSent) {
			return "SUCCESS";
		}
		return "FAIL";
	}

	public String signIn(LoginForm login) {

		UserAccountEntity entity = userRepository.findByEmailAndPassword(login.getEmail(), login.getPwd());

		if (entity == null) {

			return "invalid credentials";
		}

		if (entity != null && entity.getAccStatus().equals("LOCKED")) {

			return "Your Account is Locked";
		}

		return "SUCESS";
	}

	public String forgotPwd(String emailId) {

		UserAccountEntity entity = userRepository.findByEmail(emailId);

		if (entity == null) {
			return "Invalid EmailId";
		}

		String fileName = "RECOVER-PASSWORD-EMAIL-BODY-TEMPLATE.txt";
		String subject="User Registration - Ashok IT";
		String body=readMailBodyContent(fileName, entity);
		boolean isSent = emailUtils.sendEmail(emailId, subject, body);
		
		if(isSent) {
			return "Password send to registered email";
		}
		return "ERROR";

	}

	public String accUnlock(UnlockAccForm unlockAcctFrom) {

		if (!(unlockAcctFrom.getNewPwd().equals(unlockAcctFrom.getConfirmNewPwd()))) {

			return "Password and ConfirmPassword should be same ...";
		}

		UserAccountEntity entity = userRepository.findByEmailAndPassword(unlockAcctFrom.getEmail(),
				unlockAcctFrom.getTempPwd());

		if (entity == null) {
			return "Incorrect Temporary Passeword";
		}

		entity.setPassword(unlockAcctFrom.getNewPwd());
		entity.setAccStatus("UNLOCKED");

		userRepository.save(entity);

		return "accUnlocked ...";
	}

	public Map<Integer, String> loadCuntries() {

		List<CountryMasterEntity> countries = countryRepository.findAll();

		Map<Integer, String> countryMap = new HashMap<Integer, String>();

		for (CountryMasterEntity entity : countries) {
			countryMap.put(entity.getCountryId(), entity.getCountryName());
		}

		return countryMap;
	}

	public Map<Integer, String> loadStates(int countryId) {

		Map<Integer, String> stateMap = new HashMap<Integer, String>();

		List<StateMasterEntity> states = stateRepository.findByCountryId(countryId);

		for (StateMasterEntity entity : states) {
			stateMap.put(entity.getStateId(), entity.getStateName());
		}

		return stateMap;
	}

	public Map<Integer, String> loadCities(int stateId) {

		Map<Integer, String> cityMap = new HashMap<Integer, String>();

		List<CityMasterEntity> cities = cityRepoSitory.findByStateId(stateId);

		for (CityMasterEntity entity : cities) {
			cityMap.put(entity.getCityId(), entity.getCityName());
		}
		return cityMap;
	}

	@Override
	public String emailCheck(String emailId) {

		UserAccountEntity findByEmail = userRepository.findByEmail(emailId);

		if (findByEmail == null) {
			return "UNIQUE";
		}

		return "DUPLICATE";
	}

	private static String generateRandomPassword(int len) {

		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghi" + "jklmnopqrstuvwxyz!@#$%&";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));

		return sb.toString();
	}

	private String readMailBodyContent(String fileName, UserAccountEntity entity) {
		String mailBody = null;

		try {
			StringBuffer sb = new StringBuffer();
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine(); // Reading First Line Data

			while (line != null) {
				sb.append(line);// appending line data to bufferedReader Object
				line = br.readLine();// readaing next line
			}

			mailBody = sb.toString();
			mailBody.replace("{FNAME}", entity.getFname());
			mailBody.replace("{LNAME}", entity.getLname());
			mailBody.replace("{TEMP-PWD}", entity.getPassword());
			mailBody.replace("{EMAIL}", entity.getEmail());
			mailBody.replace("{PWD}", entity.getFname());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailBody;
	}
}
package com.ashokit.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.binding.UserForm;
import com.ashokit.service.UserMgmtService;

@RestController
public class RegistrationRestController {

	@Autowired
	private UserMgmtService userMgmtService;

	@GetMapping("/countries")
	public Map<Integer, String> getCountries() {

		return userMgmtService.loadCuntries();
	}

	@GetMapping("/states/{countryId}")
	public Map<Integer, String> getStates(@PathVariable("countryId") Integer countryId) {
		return userMgmtService.loadStates(countryId);
	}

	@GetMapping("/cities/{stateId}")
	public Map<Integer, String> getCities(@PathVariable("stateId") Integer stateId) {
		return userMgmtService.loadCities(stateId);
	}

	@GetMapping("/email/{emailId}")
	public ResponseEntity<String> emailCheck(@PathVariable("emailId") String emailId) {
		String msg = userMgmtService.emailCheck(emailId);
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}

	@PostMapping("/user")
	public String userReg(@RequestBody UserForm userForm) {
		return userMgmtService.signUp(userForm);
	}

}

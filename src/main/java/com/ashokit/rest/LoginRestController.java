package com.ashokit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.binding.LoginForm;
import com.ashokit.service.UserMgmtService;

@RestController
public class LoginRestController {

	@Autowired
	private UserMgmtService userMgmtService;

	@PostMapping("/login")
	public String contact(@RequestBody LoginForm form) {
		return userMgmtService.signIn(form);
	}

}

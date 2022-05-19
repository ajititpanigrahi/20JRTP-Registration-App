package com.ashokit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.service.UserMgmtService;

@RestController
public class ForgotPwdRestController {

	@Autowired
	private UserMgmtService userMgmtService;

	@GetMapping("/forgetPw/{emailId}")
	public String forgotPwd(@PathVariable("emailId") String emailId) {
		return userMgmtService.forgotPwd(emailId);
	}

}

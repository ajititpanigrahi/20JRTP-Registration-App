package com.ashokit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.binding.UnlockAccForm;
import com.ashokit.service.UserMgmtService;

@RestController
public class UnlockAccRestController {

	@Autowired
	private UserMgmtService userMgmtService;

	@PostMapping("/unlock")
	public String unlockAcct(@RequestBody UnlockAccForm unlockAccForm) {
		return userMgmtService.accUnlock(unlockAccForm);

	}
}

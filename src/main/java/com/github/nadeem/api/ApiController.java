package com.github.nadeem.api;

import static com.github.nadeem.Constants.REQ_ATTR_ACTIVE_USER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.nadeem.api.interceptors.TokenRequired;
import com.github.nadeem.service.TokenService;
import com.github.nadeem.support.ActiveUser;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

	@Autowired
	private TokenService tokenService;

	@GetMapping("/notsecured")
	@ResponseBody
	public ResponseEntity<String> notsecured() {
		return new ResponseEntity<String>("not secured", HttpStatus.OK);
	}

	@TokenRequired
	@GetMapping("/test")
	@ResponseBody
	public ResponseEntity<String> test(@RequestAttribute(REQ_ATTR_ACTIVE_USER) ActiveUser activeUser) {
		return new ResponseEntity<String>("secured", HttpStatus.OK);
	}

	@TokenRequired
	@PostMapping("/reauth")
	@ResponseBody
	public ResponseEntity<String> reauth(@RequestAttribute(REQ_ATTR_ACTIVE_USER) ActiveUser activeUser) {

		String token = tokenService.getToken(activeUser);

		return new ResponseEntity<String>(token, HttpStatus.OK);
	}
}
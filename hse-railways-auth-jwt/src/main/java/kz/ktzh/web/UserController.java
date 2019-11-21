package kz.ktzh.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import kz.ktzh.models.UserCreationRequest;
import kz.ktzh.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userServ;

	@PostMapping(value = "/register")
	public String regUser(@RequestBody UserCreationRequest userCreationRequest) {
		return userServ.regUser(userCreationRequest.getUser(), userCreationRequest.getUserInfo());
	}

}

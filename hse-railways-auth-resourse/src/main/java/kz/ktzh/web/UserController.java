package kz.ktzh.web;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import kz.ktzh.models.UserInfo;

import kz.ktzh.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userServ;

	@PostMapping(value = "/userinfo")
	public UserInfo getUserInfo(@RequestParam String username) {
		return userServ.getUserInfo(username);
	}

	@PostMapping(value = "/updateprofile")
	public String regUser(@RequestBody UserInfo userInfo) {
		return userServ.updateUserInfo(userInfo);
	}

}

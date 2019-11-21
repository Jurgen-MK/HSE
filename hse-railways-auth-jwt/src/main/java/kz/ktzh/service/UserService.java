package kz.ktzh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kz.ktzh.models.UserInfo;
import kz.ktzh.models.UserInfoRepository;
import kz.ktzh.models.UserRepository;
import kz.ktzh.models.Authorities;
import kz.ktzh.models.UserRoleRepository;
import kz.ktzh.models.Users;

@Service
public class UserService {

	private static final String ROLE = "ROLE_USER";

	@Autowired
	UserInfoRepository userInfoRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	UserRoleRepository userRoleRepo;

	@Autowired
	PasswordEncoder passwordEnc;

	public String regUser(Users user, UserInfo userInfo) {
		if (userRepo.findByUsername(user.getUsername()).isEmpty()) {
			try {
				user.setPassword(passwordEnc.encode(user.getPassword()));
				System.out.println("bcrypt - " + user.getPassword());
				userRepo.save(user);
				userRoleRepo.save(new Authorities(user.getUsername(), ROLE));
				List<Users> tempuser = userRepo.findByUsername(user.getUsername());
				userInfo.setId(tempuser.get(0).getId());
				userInfoRepo.save(userInfo);
			} catch (Exception e) {
				e.printStackTrace();
				return "Ошибка регистрации";
			}
		} else {
			return "Пользователь с таким логином существует";
		}
		return "Успешная регистрация";
	}

}

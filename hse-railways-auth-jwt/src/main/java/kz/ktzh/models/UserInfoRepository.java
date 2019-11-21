package kz.ktzh.models;

import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
	UserInfo findByUsername(String username);
}

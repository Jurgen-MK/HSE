package kz.ktzh;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import kz.ktzh.models.Authorities;
import kz.ktzh.models.UserInfo;
import kz.ktzh.models.UserInfoRepository;
import kz.ktzh.models.UserRepository;
import kz.ktzh.models.UserRoleRepository;
import kz.ktzh.models.Users;
import kz.ktzh.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	
	@MockBean
	UserInfoRepository userInfoMock;

	@MockBean
	UserRepository userRepoMock;
	
	@MockBean
	UserRoleRepository userRoleMock;
	
	@Autowired
	UserService userImpl;
	
	private String teststring;	
	private Users testuser;
	private UserInfo testuserinfo;	
	private Authorities testauth;
	private List<Users> testuserlist;
	
	@Before
	public void init() {
		teststring = "1";		
		testuserinfo = new UserInfo(1, "1", "1", "1", "1", "1");		
		testauth = new Authorities("1", "1");
		testuser = new Users("1", "1", (byte) 1);
		testuserlist = new ArrayList<Users>();
	}

	@Test
	public void testRegUserCorrect() {
		when(userRepoMock.findByUsername(Mockito.anyString())).thenReturn(testuserlist);		
		when(userRepoMock.save(Mockito.any(Users.class))).thenReturn(testuser);
		when(userInfoMock.save(Mockito.any(UserInfo.class))).thenReturn(testuserinfo);
		when(userRoleMock.save(Mockito.any(Authorities.class))).thenReturn(testauth);		
		assertEquals("Успешная регистрация", userImpl.regUser(testuser, testuserinfo));
	}

	@Test
	public void testRegUserWrong() {
		testuserlist.add(testuser);
		when(userRepoMock.findByUsername(Mockito.anyString())).thenReturn(testuserlist);	
		when(userRepoMock.save(Mockito.any(Users.class))).thenReturn(testuser);
		when(userInfoMock.save(Mockito.any(UserInfo.class))).thenReturn(testuserinfo);
		when(userRoleMock.save(Mockito.any(Authorities.class))).thenReturn(testauth);	
		assertEquals("Пользователь с таким логином существует", userImpl.regUser(testuser, testuserinfo));
	}

	@Test(expected = Exception.class)
	public void testRegUserError() {
		when(userRepoMock.findByUsername(Mockito.anyString())).thenReturn(testuserlist);		
		when(userRepoMock.save(Mockito.any(Users.class))).thenThrow(Exception.class);
		assertEquals("Ошибка регистрации", userImpl.regUser(testuser, testuserinfo));
	}
	
}

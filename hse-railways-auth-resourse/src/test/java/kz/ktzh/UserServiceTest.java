package kz.ktzh;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import kz.ktzh.models.UserInfo;
import kz.ktzh.models.UserInfoRepository;
import kz.ktzh.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	UserService userImpl;
	
	@MockBean
	UserInfoRepository userInfoMock;
	
	private UserInfo testuserinfo;
	
	@Before
	public void init() {
		testuserinfo = new UserInfo(1, "1", "1", "1", "1", "1");
	}
	
	@Test
	public void testGetUserInfo() {
		when(userInfoMock.findByUsername(Mockito.anyString())).thenReturn(testuserinfo);
		assertEquals(testuserinfo, userImpl.getUserInfo("user"));
	}
	
	@Test
	public void testUpdateUserInfoCorrect() {
		when(userInfoMock.save(Mockito.any(UserInfo.class))).thenReturn(testuserinfo);
		assertEquals("1", userImpl.updateUserInfo(testuserinfo));
	}
	
	@Test(expected = Exception.class)
	public void testUpdateUserInfoError() {
		when(userInfoMock.save(Mockito.any(UserInfo.class))).thenThrow(Exception.class);
		assertEquals("0", userImpl.updateUserInfo(testuserinfo));		
	}
	
	
}

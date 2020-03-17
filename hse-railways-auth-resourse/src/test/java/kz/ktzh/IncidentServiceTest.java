package kz.ktzh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kz.ktzh.models.Incidents;
import kz.ktzh.models.IncidentsRepository;
import kz.ktzh.service.IncidentDAO;
import kz.ktzh.service.IncidentService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IncidentServiceTest {

	@Autowired
	IncidentService incImpl;

	@MockBean
	IncidentDAO incdaoMock;

	@MockBean
	IncidentsRepository incRepoMock;

	Incidents testinc;
	List<Incidents> testinclist;
	MockMultipartFile file;

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Before
	public void init() {
		testinc = new Incidents(1337666, new Date(), "1", 1, "1");
		testinclist = new ArrayList<Incidents>();
		file = new MockMultipartFile("file", "test.jpg", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
	}
	
	@Test
	public void testInsertIncidentCorrect() throws IllegalStateException, IOException {			
		when(incRepoMock.save(Mockito.any(Incidents.class))).thenReturn(testinc);
		assertEquals(true, incImpl.insertIncident(testinc, file));		
	}
	
	@Test(expected = Exception.class)
	public void testInsertIncidentWrong() {	
		when(incRepoMock.save(Mockito.any(Incidents.class))).thenThrow(Exception.class);
		assertEquals(false, incImpl.insertIncident(testinc, file));
	}

	@Test
	public void testListAllInc() {
		testinclist.add(testinc);
		when(incRepoMock.findAll()).thenReturn(testinclist);
		assertEquals(testinclist, incImpl.listAllInc());
	}

	@Test
	public void testListIncById() {
		testinclist.add(testinc);
		when(incRepoMock.findByUserid(Mockito.any(Integer.class))).thenReturn(testinclist);
		assertEquals(testinclist, incImpl.listIncById(1));
	}

	@Test
	public void testRemoveIncidentCorrect() throws IOException {
		IncidentService incspy = Mockito.spy(IncidentService.class);
		doReturn(true).when(incspy).removeIncident(1);
		when(incdaoMock.selectFilePathById(Mockito.any(Integer.class))).thenReturn("1");		
		doNothing().when(incdaoMock).removeIncident(Mockito.any(Integer.class));
		assertEquals(true, incspy.removeIncident(1));
	}
	
	@Test
	public void testRemoveIncidentWrong() throws IOException {		
		when(incdaoMock.selectFilePathById(Mockito.any(Integer.class))).thenReturn("1");		
		doNothing().when(incdaoMock).removeIncident(Mockito.any(Integer.class));
		assertEquals(false, incImpl.removeIncident(1));
	}
	
	
	
	@After
	public void destruct() throws IOException {
		File f = new File("C:\\HSEKTZH\\UID1337666\\");
		FileUtils.deleteDirectory(f);
	}
	

}

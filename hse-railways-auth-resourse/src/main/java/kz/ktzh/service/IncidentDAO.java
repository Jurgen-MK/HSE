package kz.ktzh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IncidentDAO {
	

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void removeIncident(int incid) {
		jdbcTemplate.update("delete from incidents where inc_id = ?", incid);		
	}
	
	public String selectFilePathById(int incid) {
		return jdbcTemplate.queryForObject("SELECT content_path FROM incidents WHERE inc_id = ?",
				new Object[] { incid }, String.class);
	}

}

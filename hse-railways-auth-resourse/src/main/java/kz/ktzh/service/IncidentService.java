package kz.ktzh.service;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kz.ktzh.models.Incidents;
import kz.ktzh.models.IncidentsRepository;

@Service
public class IncidentService {

	@Autowired
	IncidentsRepository incRepo;

	public Boolean insertIncident(Incidents incds, MultipartFile mpfile) {
		try {
			Timestamp dt = new Timestamp(System.currentTimeMillis());
			String basePath = "C:\\HSEKTZH\\";
			String pathDocs = new StringBuilder().append("UID").append(incds.getUser_id()).append("\\")
					.append(dt.toString().substring(0, 4)).append("\\").append(dt.toString().substring(5, 7))
					.append("\\").append(dt.toString().substring(8, 10)).append("\\").toString();			
			File file = new File(mpfile.getOriginalFilename());
			String simpleFileName = file.getName();
			//String destination = basePath + pathDocs + dt.getTime() + simpleFileName;
			String destination = new StringBuilder().append(basePath).append(pathDocs).append(dt.getTime()).append(simpleFileName).toString();
			if (new File(basePath + pathDocs).mkdirs()) {
				System.out.println("path created");
			}
			//incds.setContent_path(pathDocs.replace("\\", "/") + dt.getTime() + simpleFileName);
			incds.setContent_path(new StringBuilder().append(pathDocs.replace("\\", "/")).append(dt.getTime()).append(simpleFileName).toString());
			file = new File(destination);
			mpfile.transferTo(file);
			incRepo.save(incds);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Iterable<Incidents> listAllInc() {
		return incRepo.findAll();
	}

	public List<Incidents> listIncById(Integer user_id) {
		return incRepo.findByUserid(user_id);
	}

}

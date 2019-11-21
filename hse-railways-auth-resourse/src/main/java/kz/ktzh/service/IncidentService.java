package kz.ktzh.service;

import java.io.File;
import java.sql.Timestamp;

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
			String pathDocs = "UID" + incds.getUser_id() + "\\";
			pathDocs = pathDocs + dt.toString().substring(0, 4) + "\\" + dt.toString().substring(5, 7) + "\\"
					+ dt.toString().substring(8, 10);

			pathDocs = pathDocs + "\\";
			File file = new File(mpfile.getOriginalFilename());
			String simpleFileName = file.getName();
			String destination = basePath + pathDocs + dt.getTime() + simpleFileName;
			if (new File(basePath + pathDocs).mkdirs()) {

			}
			incds.setContent_path(pathDocs.replace("\\", "/") + dt.getTime() + simpleFileName);
			file = new File(destination);
			System.out.println(destination);
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

	public Iterable<Incidents> listIncById(Integer user_id) {
		return incRepo.findByUserid(user_id);
	}

}

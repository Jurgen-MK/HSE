package kz.ktzh.web;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import kz.ktzh.models.Incidents;
import kz.ktzh.service.IncidentService;

@RestController
@RequestMapping("/incidents")
public class IncidentsController {

	@Autowired
	IncidentService incServ;

	@PostMapping(value = "/sendinc", consumes = { "application/json", "multipart/form-data" }, produces = "text/plain")
	public String createRecord(@RequestPart("incds") Incidents incds, @RequestPart("mpfile") MultipartFile mpfile) {
		if (incServ.insertIncident(incds, mpfile)) {
			return "Малява пришла";
		} else {
			return "Что-то пошло не так";
		}

	}

	@PostMapping(value = "/viewall")
	public ResponseEntity<Iterable<Incidents>> getAllInc() {
		return ResponseEntity.ok(incServ.listAllInc());
	}

	@PostMapping(value = "/viewbyid")
	public ResponseEntity<Iterable<Incidents>> getIncById(@RequestParam Integer userid) {
		return ResponseEntity.ok(incServ.listIncById(userid));
	}

	@GetMapping("/image/**")
	public HttpEntity<byte[]> getPhoto(HttpServletRequest request) throws IOException {
		String basepath = "C:\\HSEKTZH\\";
		// C:\HSEKTZH\UID1\2019\09\11\
		System.out.println("ПЕРВЫЙ ПАШЙОЛ");
		String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		basepath = basepath + new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
		basepath.replace("/", "\\");
		System.out.println("БЕЙЗПАЧ - " + basepath);
		byte[] image = org.apache.commons.io.FileUtils.readFileToByteArray(new File(basepath));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		headers.setContentLength(image.length);
		return new HttpEntity<byte[]>(image, headers);
	}

}

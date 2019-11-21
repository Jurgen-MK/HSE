package kz.ktzh.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestController {

//	@RequestMapping(value = "/index", method = RequestMethod.GET)
    @GetMapping({"/index"})
	public String test() {
		return "index";
	}
    
    @GetMapping({"/login"})
    public String login() {
    	return "login";
    }
    
}

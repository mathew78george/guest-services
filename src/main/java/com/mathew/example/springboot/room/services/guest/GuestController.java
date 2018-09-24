package com.mathew.example.springboot.room.services.guest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuestController {

	@Autowired
	private GuestDataService dataService;

	@RequestMapping(value = "/guests", method = RequestMethod.GET)
	public List<Guest> findAllGuest() {
		return dataService.findAll();
	}

	@RequestMapping(value = "/findbyemail", method = RequestMethod.GET)
	public Guest findByEmail(@RequestParam(name = "emailaddress", required = true) String emailAddress) {
		return dataService.findByEmailAddress(emailAddress);
	}

	@RequestMapping(value = "/guests/{id}", method = RequestMethod.GET)
	public Guest findById(@PathVariable(name = "id") String id) {
		return dataService.findById(Long.valueOf(id));
	}
}

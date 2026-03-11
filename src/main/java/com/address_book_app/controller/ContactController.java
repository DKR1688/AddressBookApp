package com.address_book_app.controller;

import com.address_book_app.model.Contact;
import com.address_book_app.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

	@Autowired
	private ContactService service;

	@GetMapping("/")
	public String home() {
		return "Welcome to Address Book Application";
	}

	@GetMapping
	public List<Contact> getAll() {
		return service.getAll();
	}

	@GetMapping("/{phoneNumber}")
	public Contact getByPhone(@PathVariable String phoneNumber) {
		return service.getByPhone(phoneNumber);
	}

	@PostMapping
	public Contact add(@RequestBody Contact contact) {
		return service.add(contact);
	}

	@PutMapping("/{phoneNumber}")
	public Contact update(@PathVariable String phoneNumber, @RequestBody Contact contact) {
		return service.update(phoneNumber, contact);
	}

	@DeleteMapping("/{phoneNumber}")
	public String delete(@PathVariable String phoneNumber) {
		service.delete(phoneNumber);
		return "Deleted Successfully";
	}
}

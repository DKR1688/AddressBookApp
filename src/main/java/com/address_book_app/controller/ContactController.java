package com.address_book_app.controller;

import com.address_book_app.model.Contact;
import com.address_book_app.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {
	private final ContactService service;

	public ContactController(ContactService service) {
		this.service = service;
	}

	@GetMapping
	public List<Contact> getAll() {
		return service.getAll();
	}

	@GetMapping("/{id}")
	public Contact getById(@PathVariable Long id) {
		return service.getById(id);
	}

	@PostMapping
	public ResponseEntity<Contact> add(@RequestBody Contact contact) {
		return ResponseEntity.ok(service.add(contact));
	}

	@PutMapping("/{id}")
	public Contact update(@PathVariable Long id, @RequestBody Contact contact) {
		return service.update(id, contact);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.ok("Deleted Successfully");
	}
}
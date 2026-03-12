package com.address_book_app.controller;

import com.address_book_app.model.*;
import com.address_book_app.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/addressbooks/{bookName}/contacts")
public class ContactController {
	private final AddressBookService service;

	public ContactController(AddressBookService service) {
		this.service = service;
	}

	// getting all contacts
	@GetMapping
	public List<Contact> getAll(@PathVariable String bookName) {
		return service.getContacts(bookName);
	}

	// here adding contact
	@PostMapping
	public Contact add(@PathVariable String bookName, @RequestBody Contact contact) {
		return service.addContact(bookName, contact);
	}

	// it's to delete contact
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable String bookName, @PathVariable Long id) {
		boolean deleted = service.deleteContact(bookName, id);
		if (!deleted) {
			return ResponseEntity.badRequest().body("Contact not found");
		}

		return ResponseEntity.ok("Deleted Successfully");
	}
}
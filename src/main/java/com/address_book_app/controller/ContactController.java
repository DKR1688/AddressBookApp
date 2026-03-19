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

	@GetMapping
	public List<Contact> getAll(@PathVariable String bookName) {
		return service.getContacts(bookName);
	}
	
	@GetMapping("/{id}")
	public Contact getById(@PathVariable String bookName, @PathVariable Long id) {
	    return service.getContactById(bookName, id);
	}

	@PostMapping
	public Contact add(@PathVariable String bookName, @RequestBody Contact contact) {
		return service.addContact(bookName, contact);
	}

	@PutMapping("/{id}")
	public Contact update(@PathVariable String bookName, @PathVariable Long id, @RequestBody Contact updatedContact) {
		return service.updateContact(bookName, id, updatedContact);
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
	
	// UC8 - Search persons by city or state across all address books
	@GetMapping("/search/city/{city}")
	public List<Contact> searchByCity(@PathVariable String city) {
		return service.searchPersonsByCity(city);
	}
	
	@GetMapping("/search/state/{state}")
	public List<Contact> searchByState(@PathVariable String state) {
		return service.searchPersonsByState(state);
	}
	
	// UC9 - View persons by city or state across all address books
	@GetMapping("/view/by-city")
	public Map<String, List<Contact>> viewPersonsByCity() {
		return service.getPersonsByCity();
	}
	
	@GetMapping("/view/by-state")
	public Map<String, List<Contact>> viewPersonsByState() {
		return service.getPersonsByState();
	}
	
	// UC10 - Count persons by city or stateacross all address books
	@GetMapping("/count/by-city")
	public Map<String, Long> countByCity() {
		return service.getCountByCity();
	}
	
	@GetMapping("/count/by-state")
	public Map<String, Long> countByState() {
		return service.getCountByState();
	}
	
	// UC11 - Sort all persons by name across all address books and in a specific address book
	@GetMapping("/sorted/all-by-name")
	public List<Contact> getAllSortedByName() {
		return service.getSortedPersonsByName();
	}
	
	@GetMapping("/sorted/by-name")
	public List<Contact> getSortedByName(@PathVariable String bookName) {
		return service.getSortedPersonsByNameInBook(bookName);
	}
	
	// UC12 - Sort all persons by city or state or Zip across all address books
	@GetMapping("/sorted/all-by-city")
	public List<Contact> getAllSortedByCity() {
		return service.getSortedPersonsByCity();
	}
	
	@GetMapping("/sorted/all-by-state")
	public List<Contact> getAllSortedByState() {
		return service.getSortedPersonsByState();
	}
	
	@GetMapping("/sorted/all-by-zip")
	public List<Contact> getAllSortedByZip() {
		return service.getSortedPersonsByZip();
	}
	
	// UC12 - Sort persons by city or state or Zip in a specific address book
	@GetMapping("/sorted/by-city")
	public List<Contact> getSortedByCity(@PathVariable String bookName) {
		return service.getSortedPersonsByCityInBook(bookName);
	}
	
	@GetMapping("/sorted/by-state")
	public List<Contact> getSortedByState(@PathVariable String bookName) {
		return service.getSortedPersonsByStateInBook(bookName);
	}
	
	@GetMapping("/sorted/by-zip")
	public List<Contact> getSortedByZip(@PathVariable String bookName) {
		return service.getSortedPersonsByZipInBook(bookName);
	}
}
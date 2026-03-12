package com.address_book_app.service;

import java.util.*;
import org.springframework.stereotype.Service;
import com.address_book_app.model.AddressBook;
import com.address_book_app.model.Contact;

@Service
public class AddressBookService {
	private Map<String, AddressBook> addressBookMap = new HashMap<>();
	private Long idCounter = 1L;

	public AddressBook createAddressBook(String name) {
		AddressBook book = new AddressBook(name);
		addressBookMap.put(name, book);
		return book;
	}

	public Set<String> getAllAddressBooks() {
		return addressBookMap.keySet();
	}

	public AddressBook getAddressBook(String name) {
		return addressBookMap.get(name);
	}

	public Contact addContact(String bookName, Contact contact) {
	    AddressBook book = addressBookMap.get(bookName);
	    if (book == null) {
	    	throw new RuntimeException("AddressBook not found");
	    }

	    //UC7 to check duplicates
		boolean duplicate = book.getContacts().stream()
				.anyMatch(c -> c.getFirstName().equalsIgnoreCase(contact.getFirstName())
						&& c.getLastName().equalsIgnoreCase(contact.getLastName()));
		if (duplicate) {
			throw new RuntimeException("Duplicate person entry not allowed");
		}
		
	    contact.setId(idCounter++);
	    book.getContacts().add(contact);
	    return contact;
	}

	public List<Contact> getContacts(String bookName) {
		AddressBook book = addressBookMap.get(bookName);
		if (book == null)
			throw new RuntimeException("AddressBook not found");

		return book.getContacts();
	}
	
	public Contact getContactById(String bookName, Long id) {
		AddressBook book = addressBookMap.get(bookName);
		if (book == null)
			throw new RuntimeException("AddressBook not found");

		return book.getContacts().stream().filter(c -> c.getId().equals(id)).findFirst()
				.orElseThrow(() -> new RuntimeException("Contact not found"));
	}
	
	public Contact updateContact(String bookName, Long id, Contact updated) {
		AddressBook book = addressBookMap.get(bookName);
		if (book == null)
			throw new RuntimeException("AddressBook not found");

		Contact existing = book.getContacts().stream().filter(c -> c.getId().equals(id)).findFirst()
				.orElseThrow(() -> new RuntimeException("Contact not found"));

		existing.setFirstName(updated.getFirstName());
		existing.setLastName(updated.getLastName());
		existing.setAddress(updated.getAddress());
		existing.setCity(updated.getCity());
		existing.setState(updated.getState());
		existing.setZip(updated.getZip());
		existing.setPhoneNumber(updated.getPhoneNumber());
		existing.setEmail(updated.getEmail());

		return existing;
	}

	public boolean deleteContact(String bookName, Long id) {
		AddressBook book = addressBookMap.get(bookName);
		if (book == null)
			return false;

		return book.getContacts().removeIf(c -> c.getId().equals(id));
	}
}
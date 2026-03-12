package com.address_book_app.service;

import java.util.*;
import org.springframework.stereotype.Service;
import com.address_book_app.model.AddressBook;
import com.address_book_app.model.Contact;

@Service
public class AddressBookService {
	private Map<String, AddressBook> addressBookMap = new HashMap<>();

	// create address book
	public AddressBook createAddressBook(String name) {
		AddressBook book = new AddressBook(name);
		addressBookMap.put(name, book);
		return book;
	}

	// get all address books
	public Set<String> getAllAddressBooks() {
		return addressBookMap.keySet();
	}

	// get address book
	public AddressBook getAddressBook(String name) {
		return addressBookMap.get(name);
	}

	// add contact
	public Contact addContact(String bookName, Contact contact) {
		AddressBook book = addressBookMap.get(bookName);
		if (book == null)
			throw new RuntimeException("AddressBook not found");

		book.getContacts().add(contact);
		return contact;
	}

	// get contacts
	public List<Contact> getContacts(String bookName) {
		AddressBook book = addressBookMap.get(bookName);
		if (book == null)
			throw new RuntimeException("AddressBook not found");

		return book.getContacts();
	}

	// delete contact
	public boolean deleteContact(String bookName, Long id) {
		AddressBook book = addressBookMap.get(bookName);
		if (book == null)
			return false;

		return book.getContacts().removeIf(c -> c.getId().equals(id));
	}
}
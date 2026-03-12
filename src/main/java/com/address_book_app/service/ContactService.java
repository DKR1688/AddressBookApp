package com.address_book_app.service;

import com.address_book_app.model.Contact;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {
	//UC5- it's local list to store multiple person in address book
	private final List<Contact> contactList = new ArrayList<>();
	private Long idCounter = 1L;

	public List<Contact> getAll() {
		return contactList;
	}

	public Contact getById(Long id) {
		return contactList.stream().filter(c -> c.getId().equals(id)).findFirst()
				.orElseThrow(() -> new RuntimeException("Contact not found"));
	}

	public Contact add(Contact contact) {
		contact.setId(idCounter++);
		contactList.add(contact);
		return contact;
	}

	public Contact update(Long id, Contact updated) {
		Contact existing = getById(id);

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

	public void delete(Long id) {
		Contact contact = getById(id);
		contactList.remove(contact);
	}
	
	//UC3--- editing existing contact by name
	public Contact findByFirstName(String firstName) {
		return contactList.stream().filter(c -> c.getFirstName().equalsIgnoreCase(firstName)).findFirst().orElse(null);
	}

	public boolean editContactByName(String firstName, Contact updatedContact) {
		Contact existing = findByFirstName(firstName);

		if (existing == null) {
			return false;
		}

		existing.setFirstName(updatedContact.getFirstName());
		existing.setLastName(updatedContact.getLastName());
		existing.setAddress(updatedContact.getAddress());
		existing.setCity(updatedContact.getCity());
		existing.setState(updatedContact.getState());
		existing.setZip(updatedContact.getZip());
		existing.setPhoneNumber(updatedContact.getPhoneNumber());
		existing.setEmail(updatedContact.getEmail());

		return true;
	}
	
	//UC4 --- deleting contact by name
	public boolean deleteByFirstName(String firstName) {
		Contact contact = contactList.stream().filter(c -> c.getFirstName().equalsIgnoreCase(firstName)).findFirst()
				.orElse(null);

		if (contact == null) {
			return false;
		}

		contactList.remove(contact);
		return true;
	}
}
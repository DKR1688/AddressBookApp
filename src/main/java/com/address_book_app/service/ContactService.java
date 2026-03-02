package com.address_book_app.service;

import com.address_book_app.model.Contact;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ContactService {

	private final List<Contact> contactList = new ArrayList<>();
	private final AtomicLong idGenerator = new AtomicLong(1);

	public List<Contact> getAll() {
		return contactList;
	}

	public Contact getById(Long id) {
		return contactList.stream().filter(c -> c.getId().equals(id)).findFirst()
				.orElseThrow(() -> new RuntimeException("Contact not found"));
	}

	public Contact add(Contact contact) {
		contact.setId(idGenerator.getAndIncrement());
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
}
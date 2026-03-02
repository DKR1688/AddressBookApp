package com.address_book_app.service;

import com.address_book_app.model.Contact;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {

	private List<Contact> contactList = new ArrayList<>();

	public List<Contact> getAll() {
		return contactList;
	}

	public Contact getByPhone(String phoneNumber) {
		return contactList.stream().filter(c -> c.getPhoneNumber().equals(phoneNumber)).findFirst().orElse(null);
	}

	public Contact add(Contact contact) {
		contactList.add(contact);
		return contact;
	}

	public Contact update(String phoneNumber, Contact updated) {

		for (Contact c : contactList) {
			if (c.getPhoneNumber().equals(phoneNumber)) {

				c.setFirstName(updated.getFirstName());
				c.setLastName(updated.getLastName());
				c.setAddress(updated.getAddress());
				c.setCity(updated.getCity());
				c.setState(updated.getState());
				c.setZip(updated.getZip());
				c.setEmail(updated.getEmail());

				return c;
			}
		}
		return null;
	}

	public void delete(String phoneNumber) {
		contactList.removeIf(c -> c.getPhoneNumber().equals(phoneNumber));
	}
}
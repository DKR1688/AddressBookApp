package com.address_book_app.console;

import java.util.ArrayList;
import java.util.List;

public class AddressBook {

	private List<ContactPerson> contactList = new ArrayList<>();

	public void addContact(ContactPerson person) {
		contactList.add(person);
		System.out.println("Contact Added Successfully!");
	}

	public void displayAll() {
		if (contactList.isEmpty()) {
			System.out.println("Address Book is Empty.");
			return;
		}

		for (ContactPerson person : contactList) {
			person.display();
		}
	}
}
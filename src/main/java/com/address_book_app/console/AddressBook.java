package com.address_book_app.console;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

	// UC3 --- editing contact
	public void editContact(String firstName, Scanner scanner) {
		for (ContactPerson person : contactList) {
			if (person.getFirstName().equalsIgnoreCase(firstName)) {

				System.out.println("Enter new details");

				System.out.print("Address: ");
				person.setAddress(scanner.nextLine());

				System.out.print("City: ");
				person.setCity(scanner.nextLine());

				System.out.print("State: ");
				person.setState(scanner.nextLine());

				System.out.print("Zip: ");
				person.setZip(scanner.nextLine());

				System.out.print("Phone: ");
				person.setPhoneNumber(scanner.nextLine());

				System.out.print("Email: ");
				person.setEmail(scanner.nextLine());

				System.out.println("Contact Updated Successfully!");
				return;
			}
		}

		System.out.println("Contact not found!");
	}

	// UC4 --- deleting Contact
	public void deleteContact(String firstName) {
		ContactPerson removePerson = null;
		for (ContactPerson person : contactList) {
			if (person.getFirstName().equalsIgnoreCase(firstName)) {
				removePerson = person;
				break;
			}
		}

		if (removePerson != null) {
			contactList.remove(removePerson);
			System.out.println("Contact Deleted Successfully!");
		} else {
			System.out.println("Contact not found!");
		}
	}
}
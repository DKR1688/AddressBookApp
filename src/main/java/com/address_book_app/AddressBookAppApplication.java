package com.address_book_app;

import java.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.address_book_app.model.Contact;
import com.address_book_app.service.ContactService;

@SpringBootApplication
public class AddressBookAppApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(AddressBookAppApplication.class, args);
		ContactService service = context.getBean(ContactService.class);
		Scanner scanner = new Scanner(System.in);

		Map<String, List<Contact>> addressBookMap = new HashMap<>();
		while (true) {
			System.out.println("\n------ ADDRESS BOOK SYSTEM ------");
			System.out.println("1 Create Address Book");
			System.out.println("2 Select Address Book");
			System.out.println("3 Display Address Books");
			System.out.println("4 Exit");

			int choice = scanner.nextInt();
			scanner.nextLine();
			switch (choice) {

			case 1:
				System.out.print("Enter Address Book Name: ");
				String bookName = scanner.nextLine();

				addressBookMap.putIfAbsent(bookName, new ArrayList<>());
				System.out.println("Address Book created!");
				break;

			case 2:
				System.out.print("Enter Address Book Name: ");
				String selectBook = scanner.nextLine();

				if (!addressBookMap.containsKey(selectBook)) {
					System.out.println("Address Book not found!");
				} else {
					addressBookMenu(scanner, service);
				}
				break;

			case 3:
				System.out.println("Available Address Books:");
				addressBookMap.keySet().forEach(System.out::println);
				break;

			case 4:
				System.exit(0);
			}
		}
	}

	private static void addressBookMenu(Scanner scanner, ContactService service) {
		while (true) {
			System.out.println("\n----- Address Book Menu -----");
			System.out.println("1 Add Contact");
			System.out.println("2 Edit Contact (by First Name)");
			System.out.println("3 Delete Contact (by First Name)");
			System.out.println("4 Display Contacts");
			System.out.println("5 Back");

			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				Contact contact = new Contact();
				System.out.print("First Name: ");
				contact.setFirstName(scanner.nextLine());

				System.out.print("Last Name: ");
				contact.setLastName(scanner.nextLine());

				System.out.print("Address: ");
				contact.setAddress(scanner.nextLine());

				System.out.print("City: ");
				contact.setCity(scanner.nextLine());

				System.out.print("State: ");
				contact.setState(scanner.nextLine());

				System.out.print("Zip: ");
				contact.setZip(scanner.nextLine());

				System.out.print("Phone: ");
				contact.setPhoneNumber(scanner.nextLine());

				System.out.print("Email: ");
				contact.setEmail(scanner.nextLine());

				service.add(contact);

				System.out.println("Contact Added!");
				break;

			case 2:
				System.out.print("Enter First Name to Edit: ");
				String editName = scanner.nextLine();

				Contact updated = new Contact();

				System.out.print("New Last Name: ");
				updated.setLastName(scanner.nextLine());

				System.out.print("New Address: ");
				updated.setAddress(scanner.nextLine());

				System.out.print("New City: ");
				updated.setCity(scanner.nextLine());

				System.out.print("New State: ");
				updated.setState(scanner.nextLine());

				System.out.print("New Zip: ");
				updated.setZip(scanner.nextLine());

				System.out.print("New Phone: ");
				updated.setPhoneNumber(scanner.nextLine());

				System.out.print("New Email: ");
				updated.setEmail(scanner.nextLine());

				boolean edited = service.editContactByName(editName, updated);

				System.out.println(edited ? "Contact Updated!" : "Contact not found!");

				break;

			case 3:
				System.out.print("Enter First Name to Delete: ");
				String deleteName = scanner.nextLine();

				boolean deleted = service.deleteByFirstName(deleteName);

				System.out.println(deleted ? "Contact Deleted!" : "Contact not found!");

				break;

			case 4:
				List<Contact> contacts = service.getAll();
				if (contacts.isEmpty()) {
					System.out.println("No contacts found.");
				} else {
					contacts.forEach(c -> {
						System.out.println("\nID: " + c.getId());
						System.out.println("Name: " + c.getFirstName() + " " + c.getLastName());
						System.out.println("City: " + c.getCity());
						System.out.println("Phone: " + c.getPhoneNumber());
					});
				}
				break;

			case 5:
				return;
			}
		}
	}
}
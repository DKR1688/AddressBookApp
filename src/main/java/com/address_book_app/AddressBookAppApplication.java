package com.address_book_app;

import java.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.address_book_app.model.*;
import com.address_book_app.service.ContactService;

@SpringBootApplication
public class AddressBookAppApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(AddressBookAppApplication.class, args);
		ContactService service = context.getBean(ContactService.class);
		com.address_book_app.service.AddressBookService addressBookService = context.getBean(com.address_book_app.service.AddressBookService.class);
		Scanner scanner = new Scanner(System.in);

		Map<String, AddressBook> addressBookMap = new HashMap<>();
		while (true) {
			System.out.println("\n------ ADDRESS BOOK SYSTEM ------");
			System.out.println("1 Create Address Book");
			System.out.println("2 Select Address Book");
			System.out.println("3 Display Address Books");
			System.out.println("4 Search by City (UC8)");
			System.out.println("5 Search by State (UC8)");
			System.out.println("6 View All Persons by City");
			System.out.println("7 View All Persons by State");
			System.out.println("8 Count Persons by City");
			System.out.println("9 Count Persons by State");
			System.out.println("10 Sort All Persons by Name");
			System.out.println("11 Sort All Persons by City");
			System.out.println("12 Sort All Persons by State");
			System.out.println("13 Sort All Persons by Zip");
			System.out.println("14 Exit");

			int choice = scanner.nextInt();
			scanner.nextLine();
			switch (choice) {

			case 1:
				System.out.print("Enter Address Book Name: ");
				String bookName = scanner.nextLine();

				addressBookMap.putIfAbsent(bookName, new AddressBook(bookName));
				System.out.println("Address Book created!");
				break;

			case 2:
				System.out.print("Enter Address Book Name: ");
				String selectBook = scanner.nextLine();

				if (!addressBookMap.containsKey(selectBook)) {
					System.out.println("Address Book not found!");
				} else {
					AddressBook selectedBook = addressBookMap.get(selectBook);
					addressBookMenu(scanner, service, selectedBook, addressBookService);
				}
				break;

			case 3:
				System.out.println("Available Address Books:");
				addressBookMap.keySet().forEach(System.out::println);
				break;

			case 4:
				System.out.print("Enter City to Search: ");
				String searchCity = scanner.nextLine();
				List<Contact> cityResults = addressBookService.searchPersonsByCity(searchCity);
				if (cityResults.isEmpty()) {
					System.out.println("No persons found in city: " + searchCity);
				} else {
					System.out.println("\n--- Search Results for City: " + searchCity + " ---");
					cityResults.forEach(c -> {
						System.out.println("\nName: " + c.getFirstName() + " " + c.getLastName());
						System.out.println("Address: " + c.getAddress());
						System.out.println("City: " + c.getCity());
						System.out.println("State: " + c.getState());
						System.out.println("Zip: " + c.getZip());
						System.out.println("Phone: " + c.getPhoneNumber());
						System.out.println("Email: " + c.getEmail());
					});
				}
				break;

			case 5:
				System.out.print("Enter State to Search: ");
				String searchState = scanner.nextLine();
				List<Contact> stateResults = addressBookService.searchPersonsByState(searchState);
				if (stateResults.isEmpty()) {
					System.out.println("No persons found in state: " + searchState);
				} else {
					System.out.println("\n--- Search Results for State: " + searchState + " ---");
					stateResults.forEach(c -> {
						System.out.println("\nName: " + c.getFirstName() + " " + c.getLastName());
						System.out.println("Address: " + c.getAddress());
						System.out.println("City: " + c.getCity());
						System.out.println("State: " + c.getState());
						System.out.println("Zip: " + c.getZip());
						System.out.println("Phone: " + c.getPhoneNumber());
						System.out.println("Email: " + c.getEmail());
					});
				}
				break;

			case 6:
				System.out.println("\n------ Persons Grouped by City ------");
				Map<String, List<Contact>> personsByCity = addressBookService.getPersonsByCity();
				if (personsByCity.isEmpty()) {
					System.out.println("No persons found.");
				} else {
					personsByCity.forEach((city, contacts) -> {
						System.out.println("\n===== City: " + city + " (" + contacts.size() + " persons) =====");
						contacts.forEach(c -> {
							System.out.println("  - " + c.getFirstName() + " " + c.getLastName() + " | Phone: " + c.getPhoneNumber());
						});
					});
				}
				break;

			case 7:
				System.out.println("\n------ Persons Grouped by State ------");
				Map<String, List<Contact>> personsByState = addressBookService.getPersonsByState();
				if (personsByState.isEmpty()) {
					System.out.println("No persons found.");
				} else {
					personsByState.forEach((state, contacts) -> {
						System.out.println("\n===== State: " + state + " (" + contacts.size() + " persons) =====");
						contacts.forEach(c -> {
							System.out.println("  - " + c.getFirstName() + " " + c.getLastName() + " | Phone: " + c.getPhoneNumber());
						});
					});
				}
				break;

			case 8:
				System.out.println("\n------ Count of Persons by City ------");
				Map<String, Long> countByCity = addressBookService.getCountByCity();
				if (countByCity.isEmpty()) {
					System.out.println("No persons found.");
				} else {
					countByCity.forEach((city, count) -> {
						System.out.println(city + ": " + count + " person(s)");
					});
				}
				break;

			case 9:
				System.out.println("\n------ Count of Persons by State ------");
				Map<String, Long> countByState = addressBookService.getCountByState();
				if (countByState.isEmpty()) {
					System.out.println("No persons found.");
				} else {
					countByState.forEach((state, count) -> {
						System.out.println(state + ": " + count + " person(s)");
					});
				}
				break;

			case 10:
				System.out.println("\n------ All Persons Sorted by Name ------");
				List<Contact> sortedPersons = addressBookService.getSortedPersonsByName();
				if (sortedPersons.isEmpty()) {
					System.out.println("No persons found.");
				} else {
					sortedPersons.forEach(c -> System.out.println(c));
				}
				break;

			case 11:
				System.out.println("\n------ All Persons Sorted by City ------");
				List<Contact> sortedByCity = addressBookService.getSortedPersonsByCity();
				if (sortedByCity.isEmpty()) {
					System.out.println("No persons found.");
				} else {
					sortedByCity.forEach(c -> System.out.println(c));
				}
				break;

			case 12:
				System.out.println("\n------ All Persons Sorted by State ------");
				List<Contact> sortedByState = addressBookService.getSortedPersonsByState();
				if (sortedByState.isEmpty()) {
					System.out.println("No persons found.");
				} else {
					sortedByState.forEach(c -> System.out.println(c));
				}
				break;

			case 13:
				System.out.println("\n------ All Persons Sorted by Zip ------");
				List<Contact> sortedByZip = addressBookService.getSortedPersonsByZip();
				if (sortedByZip.isEmpty()) {
					System.out.println("No persons found.");
				} else {
					sortedByZip.forEach(c -> System.out.println(c));
				}
				break;

			case 14:
				System.exit(0);
			}
		}
	}

	private static void addressBookMenu(Scanner scanner, ContactService service, AddressBook book, com.address_book_app.service.AddressBookService addressBookService) {
		while (true) {
			System.out.println("\n----- Address Book Menu -----");
			System.out.println("1 Add Contact");
			System.out.println("2 Edit Contact (by First Name)");
			System.out.println("3 Delete Contact (by First Name)");
			System.out.println("4 Display Contacts");
			System.out.println("5 Display Contacts Sorted by Name (UC11)");
			System.out.println("6 Display Contacts Sorted by City (UC12)");
			System.out.println("7 Display Contacts Sorted by State (UC12)");
			System.out.println("8 Display Contacts Sorted by Zip (UC12)");
			System.out.println("9 Back");

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

				book.getContacts().add(contact);
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

				boolean edited = false;

				for (Contact c : book.getContacts()) {
				    if (c.getFirstName().equalsIgnoreCase(editName)) {
				        c.setLastName(updated.getLastName());
				        c.setAddress(updated.getAddress());
				        c.setCity(updated.getCity());
				        c.setState(updated.getState());
				        c.setZip(updated.getZip());
				        c.setPhoneNumber(updated.getPhoneNumber());
				        c.setEmail(updated.getEmail());

				        edited = true;
				        break;
				    }
				}
				
				System.out.println(edited ? "Contact Updated!" : "Contact not found!");
				break;

			case 3:
				System.out.print("Enter First Name to Delete: ");
				String deleteName = scanner.nextLine();

				boolean deleted = book.getContacts().removeIf(c -> c.getFirstName().equalsIgnoreCase(deleteName));
				System.out.println(deleted ? "Contact Deleted!" : "Contact not found!");
				break;

			case 4:
				List<Contact> contacts = book.getContacts();
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
				System.out.println("\n------ Contacts Sorted by Name ------");
				List<Contact> sortedContacts = addressBookService.getSortedPersonsByNameInBook(book.getName());
				if (sortedContacts.isEmpty()) {
					System.out.println("No contacts found.");
				} else {
					sortedContacts.forEach(c -> System.out.println(c));
				}
				break;

			case 6:
				System.out.println("\n------ Contacts Sorted by City ------");
				List<Contact> sortedByCity = addressBookService.getSortedPersonsByCityInBook(book.getName());
				if (sortedByCity.isEmpty()) {
					System.out.println("No contacts found.");
				} else {
					sortedByCity.forEach(c -> System.out.println(c));
				}
				break;

			case 7:
				System.out.println("\n------ Contacts Sorted by State ------");
				List<Contact> sortedByState = addressBookService.getSortedPersonsByStateInBook(book.getName());
				if (sortedByState.isEmpty()) {
					System.out.println("No contacts found.");
				} else {
					sortedByState.forEach(c -> System.out.println(c));
				}
				break;

			case 8:
				System.out.println("\n------ Contacts Sorted by Zip ------");
				List<Contact> sortedByZip = addressBookService.getSortedPersonsByZipInBook(book.getName());
				if (sortedByZip.isEmpty()) {
					System.out.println("No contacts found.");
				} else {
					sortedByZip.forEach(c -> System.out.println(c));
				}
				break;

			case 9:
				return;
			}
		}
	}
}
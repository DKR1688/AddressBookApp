package com.address_book_app;

import java.util.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.address_book_app.model.Contact;
import com.address_book_app.service.ContactService;

@SpringBootApplication
public class AddressBookAppApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AddressBookAppApplication.class, args);
		ContactService service = context.getBean(ContactService.class);

		Scanner scanner = new Scanner(System.in);
		int option;

		do { //UC3------
			System.out.println("\n----------- Address Book Menu ---------");
			System.out.println("1. Add Contact");
			System.out.println("2. Edit Contact");
			System.out.println("3. View All Contacts");
			System.out.println("4. Exit");

			System.out.print("Choose option- ");
			option = scanner.nextInt();
			scanner.nextLine();

			switch (option) {
			case 1:
				System.out.println("\nEnter Contact Details-");

				System.out.print("First Name: ");
				String firstName = scanner.nextLine();

				System.out.print("Last Name: ");
				String lastName = scanner.nextLine();

				System.out.print("Address: ");
				String address = scanner.nextLine();

				System.out.print("City: ");
				String city = scanner.nextLine();

				System.out.print("State: ");
				String state = scanner.nextLine();

				System.out.print("Zip: ");
				String zip = scanner.nextLine();

				System.out.print("Phone: ");
				String phone = scanner.nextLine();

				System.out.print("Email: ");
				String email = scanner.nextLine();

				Contact contact = new Contact(null, firstName, lastName, address, city, state, zip, phone, email);

				service.add(contact);
				System.out.println("Contact Added Successfully!");
				break;

			case 2:
				System.out.print("Enter First Name to Edit: ");
				String nameToEdit = scanner.nextLine();

				Contact existing = service.findByFirstName(nameToEdit);
				if (existing == null) {
					System.out.println("Contact not found!");
					break;
				}

				System.out.println("Enter New Details-");
				System.out.print("First Name: ");
				String newFirst = scanner.nextLine();

				System.out.print("Last Name: ");
				String newLast = scanner.nextLine();

				System.out.print("Address: ");
				String newAddress = scanner.nextLine();

				System.out.print("City: ");
				String newCity = scanner.nextLine();

				System.out.print("State: ");
				String newState = scanner.nextLine();

				System.out.print("Zip: ");
				String newZip = scanner.nextLine();

				System.out.print("Phone: ");
				String newPhone = scanner.nextLine();

				System.out.print("Email: ");
				String newEmail = scanner.nextLine();

				Contact updated = new Contact(null, newFirst, newLast, newAddress, newCity, newState, newZip, newPhone,
						newEmail);

				service.editContactByName(nameToEdit, updated);
				System.out.println("Contact Updated Successfully!");
				break;

			case 3:
				System.out.println("\nAll Contacts-");
				service.getAll().forEach(
						c -> System.out.println(c.getFirstName() + " " + c.getLastName() + " | " + c.getPhoneNumber()));
				break;
			}

		} while (option != 4);

		scanner.close();
	}
}
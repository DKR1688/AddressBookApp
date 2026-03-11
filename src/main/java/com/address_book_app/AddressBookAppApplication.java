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

		System.out.println("Welcome to Address Book Program");
		Scanner scanner = new Scanner(System.in);
		char choice;

		do {
			System.out.println("\nEnter Contact Details:");

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

			System.out.print("Phone Number: ");
			String phone = scanner.nextLine();

			System.out.print("Email: ");
			String email = scanner.nextLine();

			Contact contact = new Contact(null, firstName, lastName, address, city, state, zip, phone, email);

			service.add(contact);

			System.out.println("Contact Added Successfully!");

			System.out.print("Do you want to add another contact? (y/n): ");
			choice = scanner.next().charAt(0);
			scanner.nextLine();

		} while (choice == 'y' || choice == 'Y');

		System.out.println("\nAll Contacts:");
		service.getAll().forEach(c -> System.out.println(c.getFirstName() + " " + c.getLastName()));

		scanner.close();
	}
}
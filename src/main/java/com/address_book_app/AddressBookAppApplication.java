package com.address_book_app;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.address_book_app.console.AddressBook;
import com.address_book_app.console.ContactPerson;

@SpringBootApplication
public class AddressBookAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddressBookAppApplication.class, args);
		System.out.println("Welcome to Address Book Program");
		
		Scanner scanner = new Scanner(System.in);
        AddressBook addressBook = new AddressBook();

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

            ContactPerson person = new ContactPerson(
                    firstName, lastName, address,
                    city, state, zip,
                    phone, email
            );

            addressBook.addContact(person);

            System.out.print("Do you want to add another contact? (y/n): ");
            choice = scanner.next().charAt(0);
            scanner.nextLine(); // consume newline

        } while (choice == 'y' || choice == 'Y');

        System.out.println("\nAll Contacts:");
        addressBook.displayAll();

        scanner.close();
	}

}

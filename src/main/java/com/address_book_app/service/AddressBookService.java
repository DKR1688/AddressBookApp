package com.address_book_app.service;

import java.util.*;
import java.io.*;
import java.nio.file.*;
import org.springframework.stereotype.Service;
import com.address_book_app.model.AddressBook;
import com.address_book_app.model.Contact;
import com.opencsv.CSVWriter;
import com.opencsv.CSVReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class AddressBookService {
	private Map<String, AddressBook> addressBookMap = new HashMap<>();
	private Long idCounter = 1L;

	public AddressBook createAddressBook(String name) {
		AddressBook book = new AddressBook(name);
		addressBookMap.put(name, book);
		return book;
	}

	public Set<String> getAllAddressBooks() {
		return addressBookMap.keySet();
	}

	public AddressBook getAddressBook(String name) {
		return addressBookMap.get(name);
	}

	public Contact addContact(String bookName, Contact contact) {
	    AddressBook book = addressBookMap.get(bookName);
	    if (book == null) {
	    	throw new RuntimeException("AddressBook not found");
	    }

	    //UC7 to check duplicates
		boolean duplicate = book.getContacts().stream()
				.anyMatch(c -> c.getFirstName().equalsIgnoreCase(contact.getFirstName())
						&& c.getLastName().equalsIgnoreCase(contact.getLastName()));
		if (duplicate) {
			throw new RuntimeException("Duplicate person entry not allowed");
		}
		
	    contact.setId(idCounter++);
	    book.getContacts().add(contact);
	    return contact;
	}

	public List<Contact> getContacts(String bookName) {
		AddressBook book = addressBookMap.get(bookName);
		if (book == null)
			throw new RuntimeException("AddressBook not found");

		return book.getContacts();
	}
	
	public Contact getContactById(String bookName, Long id) {
		AddressBook book = addressBookMap.get(bookName);
		if (book == null)
			throw new RuntimeException("AddressBook not found");

		return book.getContacts().stream().filter(c -> c.getId().equals(id)).findFirst()
				.orElseThrow(() -> new RuntimeException("Contact not found"));
	}
	
	public Contact updateContact(String bookName, Long id, Contact updated) {
		AddressBook book = addressBookMap.get(bookName);
		if (book == null)
			throw new RuntimeException("AddressBook not found");

		Contact existing = book.getContacts().stream().filter(c -> c.getId().equals(id)).findFirst()
				.orElseThrow(() -> new RuntimeException("Contact not found"));

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

	public boolean deleteContact(String bookName, Long id) {
		AddressBook book = addressBookMap.get(bookName);
		if (book == null)
			return false;

		return book.getContacts().removeIf(c -> c.getId().equals(id));
	}
	
	//UC8 - Search persons by city or sate across multiple address books
	public List<Contact> searchPersonsByCity(String city) {
		return addressBookMap.values().stream()
			.flatMap(book -> book.getContacts().stream())
			.filter(contact -> contact.getCity() != null && contact.getCity().equalsIgnoreCase(city))
			.toList();
	}
	
	public List<Contact> searchPersonsByState(String state) {
		return addressBookMap.values().stream()
			.flatMap(book -> book.getContacts().stream())
			.filter(contact -> contact.getState() != null && contact.getState().equalsIgnoreCase(state))
			.toList();
	}
	
	//UC9 - View persons by city or sate
	public Map<String, List<Contact>> getPersonsByCity() {
		return addressBookMap.values().stream()
			.flatMap(book -> book.getContacts().stream())
			.collect(java.util.stream.Collectors.groupingBy(
				contact -> contact.getCity() != null ? contact.getCity() : "Unknown",
				java.util.stream.Collectors.toList()
			));
	}
	
	public Map<String, List<Contact>> getPersonsByState() {
		return addressBookMap.values().stream()
			.flatMap(book -> book.getContacts().stream())
			.collect(java.util.stream.Collectors.groupingBy(
				contact -> contact.getState() != null ? contact.getState() : "Unknown",
				java.util.stream.Collectors.toList()
			));
	}
	
	//UC10 - Get count of persons by city or state across all address books
	public Map<String, Long> getCountByCity() {
		return addressBookMap.values().stream()
			.flatMap(book -> book.getContacts().stream())
			.collect(java.util.stream.Collectors.groupingBy(
				contact -> contact.getCity() != null ? contact.getCity() : "Unknown",
				java.util.stream.Collectors.counting()
			));
	}
	
	public Map<String, Long> getCountByState() {
		return addressBookMap.values().stream()
			.flatMap(book -> book.getContacts().stream())
			.collect(java.util.stream.Collectors.groupingBy(
				contact -> contact.getState() != null ? contact.getState() : "Unknown",
				java.util.stream.Collectors.counting()
			));
	}
	
	//UC11 - Sort persons by name across all address books and in a specific address book usin streams
	public List<Contact> getSortedPersonsByName() {
		return addressBookMap.values().stream()
			.flatMap(book -> book.getContacts().stream())
			.sorted((c1, c2) -> {
				int firstNameComparison = c1.getFirstName().compareTo(c2.getFirstName());
				if (firstNameComparison != 0) {
					return firstNameComparison;
				}
				return c1.getLastName().compareTo(c2.getLastName());
			})
			.toList();
	}
	
	public List<Contact> getSortedPersonsByNameInBook(String bookName) {
		AddressBook book = addressBookMap.get(bookName);
		if (book == null)
			throw new RuntimeException("AddressBook not found");
		
		return book.getContacts().stream()
			.sorted((c1, c2) -> {
				int firstNameComparison = c1.getFirstName().compareTo(c2.getFirstName());
				if (firstNameComparison != 0) {
					return firstNameComparison;
				}
				return c1.getLastName().compareTo(c2.getLastName());
			})
			.toList();
	}
	
	//UC12 - Sort persons by city or state or Zip across all address books
	public List<Contact> getSortedPersonsByCity() {
		return addressBookMap.values().stream()
			.flatMap(book -> book.getContacts().stream())
			.sorted((c1, c2) -> {
				String city1 = c1.getCity() != null ? c1.getCity() : "";
				String city2 = c2.getCity() != null ? c2.getCity() : "";
				int cityComparison = city1.compareTo(city2);
				if (cityComparison != 0) {
					return cityComparison;
				}
				int firstNameComparison = c1.getFirstName().compareTo(c2.getFirstName());
				if (firstNameComparison != 0) {
					return firstNameComparison;
				}
				return c1.getLastName().compareTo(c2.getLastName());
			})
			.toList();
	}

	public List<Contact> getSortedPersonsByState() {
		return addressBookMap.values().stream()
			.flatMap(book -> book.getContacts().stream())
			.sorted((c1, c2) -> {
				String state1 = c1.getState() != null ? c1.getState() : "";
				String state2 = c2.getState() != null ? c2.getState() : "";
				int stateComparison = state1.compareTo(state2);
				if (stateComparison != 0) {
					return stateComparison;
				}
				int firstNameComparison = c1.getFirstName().compareTo(c2.getFirstName());
				if (firstNameComparison != 0) {
					return firstNameComparison;
				}
				return c1.getLastName().compareTo(c2.getLastName());
			})
			.toList();
	}
	
	public List<Contact> getSortedPersonsByZip() {
		return addressBookMap.values().stream()
			.flatMap(book -> book.getContacts().stream())
			.sorted((c1, c2) -> {
				String zip1 = c1.getZip() != null ? c1.getZip() : "";
				String zip2 = c2.getZip() != null ? c2.getZip() : "";
				int zipComparison = zip1.compareTo(zip2);
				if (zipComparison != 0) {
					return zipComparison;
				}
				int firstNameComparison = c1.getFirstName().compareTo(c2.getFirstName());
				if (firstNameComparison != 0) {
					return firstNameComparison;
				}
				return c1.getLastName().compareTo(c2.getLastName());
			})
			.toList();
	}
	
	//UC12 - Sort persons in a specific address book by city or state or Zip
	public List<Contact> getSortedPersonsByCityInBook(String bookName) {
		AddressBook book = addressBookMap.get(bookName);
		if (book == null)
			throw new RuntimeException("AddressBook not found");
		
		return book.getContacts().stream()
			.sorted((c1, c2) -> {
				String city1 = c1.getCity() != null ? c1.getCity() : "";
				String city2 = c2.getCity() != null ? c2.getCity() : "";
				int cityComparison = city1.compareTo(city2);
				if (cityComparison != 0) {
					return cityComparison;
				}
				int firstNameComparison = c1.getFirstName().compareTo(c2.getFirstName());
				if (firstNameComparison != 0) {
					return firstNameComparison;
				}
				return c1.getLastName().compareTo(c2.getLastName());
			})
			.toList();
	}
	
	public List<Contact> getSortedPersonsByStateInBook(String bookName) {
		AddressBook book = addressBookMap.get(bookName);
		if (book == null)
			throw new RuntimeException("AddressBook not found");
		
		return book.getContacts().stream()
			.sorted((c1, c2) -> {
				String state1 = c1.getState() != null ? c1.getState() : "";
				String state2 = c2.getState() != null ? c2.getState() : "";
				int stateComparison = state1.compareTo(state2);
				if (stateComparison != 0) {
					return stateComparison;
				}
				int firstNameComparison = c1.getFirstName().compareTo(c2.getFirstName());
				if (firstNameComparison != 0) {
					return firstNameComparison;
				}
				return c1.getLastName().compareTo(c2.getLastName());
			})
			.toList();
	}
	
	public List<Contact> getSortedPersonsByZipInBook(String bookName) {
		AddressBook book = addressBookMap.get(bookName);
		if (book == null)
			throw new RuntimeException("AddressBook not found");
		
		return book.getContacts().stream()
			.sorted((c1, c2) -> {
				String zip1 = c1.getZip() != null ? c1.getZip() : "";
				String zip2 = c2.getZip() != null ? c2.getZip() : "";
				int zipComparison = zip1.compareTo(zip2);
				if (zipComparison != 0) {
					return zipComparison;
				}
				int firstNameComparison = c1.getFirstName().compareTo(c2.getFirstName());
				if (firstNameComparison != 0) {
					return firstNameComparison;
				}
				return c1.getLastName().compareTo(c2.getLastName());
			})
			.toList();
	}
	
	//UC13 - Write address book to text file and read address book from text file
	public void writeAddressBookToFile(String bookName, String filePath) throws Exception {
		AddressBook book = addressBookMap.get(bookName);
		if (book == null)
			throw new RuntimeException("AddressBook not found");
		
		try (FileWriter writer = new FileWriter(filePath)) {
			writer.write("Address Book: " + book.getName() + "\n");
			writer.write("=".repeat(80) + "\n\n");
			
			for (Contact contact : book.getContacts()) {
				writer.write("ID: " + contact.getId() + "\n");
				writer.write("Name: " + contact.getFirstName() + " " + contact.getLastName() + "\n");
				writer.write("Address: " + contact.getAddress() + "\n");
				writer.write("City: " + contact.getCity() + "\n");
				writer.write("State: " + contact.getState() + "\n");
				writer.write("Zip: " + contact.getZip() + "\n");
				writer.write("Phone: " + contact.getPhoneNumber() + "\n");
				writer.write("Email: " + contact.getEmail() + "\n");
				writer.write("-".repeat(80) + "\n\n");
			}
		}
	}
	
	public String readAddressBookFromFile(String filePath) throws Exception {
		StringBuilder content = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
		}
		return content.toString();
	}
	
	//UC14 - Write address book to CSV file using OpenCSV and read address book from CSV file using OpenCSV
	public void writeAddressBookToCSV(String bookName, String filePath) throws Exception {
		AddressBook book = addressBookMap.get(bookName);
		if (book == null)
			throw new RuntimeException("AddressBook not found");
		
		try (FileWriter fileWriter = new FileWriter(filePath);
			 CSVWriter csvWriter = new CSVWriter(fileWriter)) {
			
			// Write header and contacts
			String[] header = {"ID", "FirstName", "LastName", "Address", "City", "State", "Zip", "Phone", "Email"};
			csvWriter.writeNext(header);
			
			for (Contact contact : book.getContacts()) {
				String[] contactData = {
					contact.getId().toString(),
					contact.getFirstName(),
					contact.getLastName(),
					contact.getAddress(),
					contact.getCity(),
					contact.getState(),
					contact.getZip(),
					contact.getPhoneNumber(),
					contact.getEmail()
				};
				csvWriter.writeNext(contactData);
			}
		}
	}
	
	public List<Contact> readAddressBookFromCSV(String bookName, String filePath) throws Exception {
		AddressBook book = addressBookMap.get(bookName);
		if (book == null)
			throw new RuntimeException("AddressBook not found");
		
		List<Contact> contacts = new ArrayList<>();
		try (FileReader fileReader = new FileReader(filePath);
			 CSVReader csvReader = new CSVReader(fileReader)) {
			
			String[] line;
			boolean isHeader = true;
			
			while ((line = csvReader.readNext()) != null) {
				if (isHeader) {
					isHeader = false;
					continue;
				}
				
				Contact contact = new Contact();
				contact.setId(Long.parseLong(line[0]));
				contact.setFirstName(line[1]);
				contact.setLastName(line[2]);
				contact.setAddress(line[3]);
				contact.setCity(line[4]);
				contact.setState(line[5]);
				contact.setZip(line[6]);
				contact.setPhoneNumber(line[7]);
				contact.setEmail(line[8]);
				
				contacts.add(contact);
			}
			
			// Adding contacts to address book
			book.getContacts().clear();
			book.getContacts().addAll(contacts);
		}
		return contacts;
	}
	
	//UC15 - Write address book to JSON file using GSON and read address book from JSON file using GSON
	public void writeAddressBookToJSON(String bookName, String filePath) throws Exception {
		AddressBook book = addressBookMap.get(bookName);
		if (book == null)
			throw new RuntimeException("AddressBook not found");
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try (FileWriter writer = new FileWriter(filePath)) {
			gson.toJson(book, writer);
		}
	}
	
	public void readAddressBookFromJSON(String bookName, String filePath) throws Exception {
		Gson gson = new Gson();
		try (FileReader reader = new FileReader(filePath)) {
			AddressBook book = gson.fromJson(reader, AddressBook.class);
			if (book != null) {
				addressBookMap.put(bookName, book);
			}
		}
	}
}
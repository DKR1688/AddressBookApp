package com.address_book_app.controller;

import java.util.Set;
import org.springframework.web.bind.annotation.*;
import com.address_book_app.model.AddressBook;
import com.address_book_app.service.AddressBookService;

@RestController
@RequestMapping("/addressbooks")
public class AddressBookController {
    private final AddressBookService service;
    public AddressBookController(AddressBookService service) {
        this.service = service;
    }

    @PostMapping("/{name}")
    public AddressBook create(@PathVariable String name) {
        return service.createAddressBook(name);
    }

    @GetMapping
    public Set<String> getAll() {
        return service.getAllAddressBooks();
    }
}
package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class UserController {

    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    Iterable<User> all() {
        return service.getUsers();
    }

    @PostMapping("/users")
    User createUser(@RequestBody User newUser) {
        return this.service.createUser(newUser);
    }

    @PostMapping("/login")
    User checkUser(@RequestBody User newUser) {
        return this.service.checkUser(newUser);
    }

    @GetMapping("/users/{id}")
    User getUser(@PathVariable String id) {
        return this.service.getUser(Long.parseLong(id));
    }

    @CrossOrigin
    @PutMapping("/users")
    User logoutUser(@RequestBody User newUser) {
        return this.service.logoutUser(newUser);
    }

    @CrossOrigin
    @PutMapping("/users/{id}")
    User updateUser(@RequestBody User newUser) {
        return this.service.updateUser(newUser);
    }

}

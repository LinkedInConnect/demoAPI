package com.linkedinConnect.demoAPI.demoAPI.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkedinConnect.demoAPI.demoAPI.models.User;
import com.linkedinConnect.demoAPI.demoAPI.repositories.UserRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/users")
	public List<User> getAllUsers() {
		Sort sortByCreatedAtDesc = new Sort(Sort.Direction.DESC, "createdAt");
		return userRepository.findAll(sortByCreatedAtDesc);
	}

	@PostMapping("/users")
	public User createUser(@Valid @RequestBody User user) {
		user.setCompleted(false);
		return userRepository.save(user);
	}

	@GetMapping(value = "/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
		return userRepository.findById(id).map(user -> ResponseEntity.ok().body(user))
				.orElse(ResponseEntity.notFound().build());
	}

	@PutMapping(value = "/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") String id, @Valid @RequestBody User user) {
		return userRepository.findById(id).map(userData -> {
			userData.setTitle(user.getTitle());
			userData.setCompleted(user.getCompleted());
			User updatedUser = userRepository.save(userData);
			return ResponseEntity.ok().body(updatedUser);
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping(value = "/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
		return userRepository.findById(id).map(user -> {
			userRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}
}
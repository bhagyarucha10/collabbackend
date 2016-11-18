package com.niit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.niit.DAO.UserDAO;
import com.niit.DAOImpl.UserDAOImpl;
import com.niit.model.Blog;
import com.niit.model.User;

@RestController
public class UserController {
	private static final Logger log=LoggerFactory.getLogger(UserDAOImpl.class);
	@Autowired
	User user;
	
	@Autowired
	UserDAO userDAO;
	
	@RequestMapping(value="/listusers",method=RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		log.debug("starting of list users");
		List<User> listOfUsers = userDAO.listUser();
		if (listOfUsers.isEmpty()) {
			return new ResponseEntity <List<User>> (HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity < List < User >> (listOfUsers, HttpStatus.OK);
	
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<User> getUser(@PathVariable("userId") String userId) {
		this.user = userDAO.getUser(userId);
		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<User>(user, HttpStatus.OK);

	}
	
	@PostMapping("/users/")
	public ResponseEntity<Void> createUser(@RequestBody User user,
			UriComponentsBuilder ucBuilder) {
		if (userDAO.getUser(user.getUserId()) != null) {

			return new ResponseEntity<Void>(HttpStatus.CONFLICT);

		}

		/*
		 * role.setId("ROLE_USER"); role.setName("ROLE_USER");
		 */
		userDAO.saveOrUpdateUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("users/{userId}/").buildAndExpand(user.getUserId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

	}
	
	@PutMapping("/users/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable("userId")String userId,@RequestBody User user){
		
		if(user==null){
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		user.setUserId(userId);
		

		
		userDAO.saveOrUpdateUser(user);

		return new ResponseEntity<User>(user, HttpStatus.OK);

	}
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<User> deleteUser(@PathVariable("userId")String userId){
	this.user = userDAO.getUser(userId);
	if(this.user==null){
		System.out.println("user not exist to delete");
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		
	}
	
	userDAO.deleteUser(userId);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	
	}


}

package com.preproduction.bobrov.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.preproduction.bobrov.entity.User;

/**
 * Repository for storing user objects
 */
public class UserRepository {

	private Map<String, User> userMap = new ConcurrentHashMap<>();
		
	
	public void create(User user) {		
		userMap.put(user.getEmail(), user);
	}

	public List<User> getAll() {
		return new ArrayList<>(userMap.values());
	}

	public User getByEmail(String email) {
		return userMap.get(email);
	}
	
}

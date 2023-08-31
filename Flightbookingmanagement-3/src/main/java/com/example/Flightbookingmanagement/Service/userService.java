package com.example.Flightbookingmanagement.Service;

import com.example.Flightbookingmanagement.Entity.user;

public interface userService {
	    user findByUsername(String username);
	    user addUser(user user);
	}


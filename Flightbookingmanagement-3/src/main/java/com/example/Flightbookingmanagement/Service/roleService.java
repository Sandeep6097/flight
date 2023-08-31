package com.example.Flightbookingmanagement.Service;

import com.example.Flightbookingmanagement.Entity.role;

public interface roleService {
	role findByName(String name);

	role createRole();

}

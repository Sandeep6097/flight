package com.example.Flightbookingmanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Flightbookingmanagement.Entity.user;

public interface UserLoginRepository extends JpaRepository<user, Integer> {
	public user findByUsername(String username);

}

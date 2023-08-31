package com.example.Flightbookingmanagement.ServiceImp;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Flightbookingmanagement.Entity.user;
import com.example.Flightbookingmanagement.Exception.UserNotFoundException;
import com.example.Flightbookingmanagement.Repository.UserLoginRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserLoginRepository userLoginRepository;

	@Override
	public UserDetails  loadUserByUsername(String username) throws UsernameNotFoundException { // TODO Auto-generated
																								// method stub
		user user = userLoginRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		}

		return (UserDetails) new user(user.getUsername(), user.getPassword(), user.getRoles());

	}

	public user addUser(user user) {
		// TODO Auto-generated method stub

		return userLoginRepository.save(user);
	}

	public user getByUsername() {
		// TODO Auto-generated method stub
		return userLoginRepository.findByUsername(null);
	}

	public List<user> getAllUser() {
		// TODO Auto-generated method stub
		return userLoginRepository.findAll();
	}

	public user updateuser(user user) throws UserNotFoundException {
		// TODO Auto-generated method stub

		Optional<user> optional = userLoginRepository.findById((int) user.getUserid());

		if (optional.isPresent()) {
			user _user = userLoginRepository.findById((int) user.getUserid()).get();
			_user.setUsername(user.getUsername());
			_user.setPassword(user.getPassword());
			_user.setRoles(user.getRoles());

			return userLoginRepository.save(_user);
		} else {
			throw new UserNotFoundException("User not found with the matching ID!");
		}

		// return optional.get();
	}
}
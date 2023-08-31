package com.example.Flightbookingmanagement.Controller;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Flightbookingmanagement.Entity.role;
import com.example.Flightbookingmanagement.Entity.user;
import com.example.Flightbookingmanagement.ServiceImp.MyUserDetailsService;

@RestController
@RequestMapping("/api")
public class RegistrationController {

	@Autowired
	private MyUserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody user user) {
        // Create a new user entity
        
        String pwd = user.getPassword();
		String bcryptpwd = passwordEncoder.encode(pwd);
		user.setPassword(bcryptpwd);
		Set<role> roles = new HashSet<>();
		roles.add(new role(1, "USER"));
		user.setRoles(roles);
		user savedUser = userDetailsService.addUser(user);
		return ResponseEntity.ok("User registered successfully");
    }
    
    @GetMapping("/secure")
    @PreAuthorize("hasRole('ROLE_USER')") // Requires ROLE_USER authority
    public String securedPage() {
        return "secure-page";
    }
}
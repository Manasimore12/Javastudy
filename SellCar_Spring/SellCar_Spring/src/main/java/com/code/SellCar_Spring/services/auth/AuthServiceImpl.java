package com.code.SellCar_Spring.services.auth;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.code.SellCar_Spring.dto.SignupRequest;
import com.code.SellCar_Spring.dto.UserDTO;
import com.code.SellCar_Spring.entities.User;
import com.code.SellCar_Spring.enums.UserRole;
import com.code.SellCar_Spring.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	private final UserRepository userRepository;
	@PostConstruct
	public void createAnAdminAccount() {
		Optional<User> optionalAdmin = userRepository.findByUserRole(UserRole.ADMIN);
		if(optionalAdmin.isEmpty()) {
			User admin = new User();
			admin.setName("Admin");
			admin.setEmail("admin@test.com");
			admin.setUserRole(UserRole.ADMIN);
			admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(admin);
			System.out.println("Admin account created successfully");
		} else {
			System.out.println("Admin account already exist!");

		}
	}
	@Override
	public Boolean hasUserWithEmail(String email) {
		
		return userRepository.findFirstByEmail(email).isPresent();
	}
	@Override
	public UserDTO signup(SignupRequest signupRequest) {
		User user = new User();
		user.setEmail(signupRequest.getEmail());
		user.setName(signupRequest.getName());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		user.setUserRole(UserRole.CUSTOMER);
		return userRepository.save(user).getUserDTO();
	}
}

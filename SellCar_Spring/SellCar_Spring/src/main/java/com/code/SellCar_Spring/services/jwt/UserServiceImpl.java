package com.code.SellCar_Spring.services.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.code.SellCar_Spring.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;

	@Override
	public UserDetailsService userDetailsService() {
		// TODO Auto-generated method stub
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
				
				return userRepository.findFirstByEmail(email)
						.orElseThrow(()-> new UsernameNotFoundException("User Not found"));
				
			}
			
		};
	}
}

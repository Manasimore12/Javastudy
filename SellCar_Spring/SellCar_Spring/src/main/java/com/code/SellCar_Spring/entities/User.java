package com.code.SellCar_Spring.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.code.SellCar_Spring.dto.UserDTO;
import com.code.SellCar_Spring.enums.UserRole;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String password;
	private UserRole userRole;
	public UserDTO getUserDTO() {
		UserDTO userDTO= new UserDTO();
		userDTO.setId(id);
		userDTO.setName(name);
		userDTO.setEmail(email);
		userDTO.setUserRole(userRole);
		return userDTO;
		
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return List.of(new SimpleGrantedAuthority(userRole.name()));
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
		
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
		
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
		
	}
	@Override
	public boolean isEnabled() {
		return true;
		
	}
}

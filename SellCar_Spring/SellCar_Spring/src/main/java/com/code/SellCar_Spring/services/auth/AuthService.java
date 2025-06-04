package com.code.SellCar_Spring.services.auth;
import com.code.SellCar_Spring.dto.*;
public interface AuthService {
	UserDTO signup(SignupRequest signupRequest);
	Boolean hasUserWithEmail(String email);
}

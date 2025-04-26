package com.example.ems.service;

import com.example.ems.dto.AuthRequest;
import com.example.ems.dto.AuthResponse;
import com.example.ems.dto.SignupRequest;
import com.example.ems.models.UserModel;
import com.example.ems.repository.UserRepository;
import com.example.ems.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Login method (existing functionality)
    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmployeeId(), request.getPassword())
        );

        UserDetails user = (UserDetails) authentication.getPrincipal(); // Get user details
        String token = jwtProvider.generateToken(user); // Generate JWT token

        UserModel model = (UserModel) user; // Cast to UserModel to get custom fields

        // Return response with token, role, and name
        return new AuthResponse(token, model.getRole(), model.getName());
    }

    // Signup method (new functionality)
    public AuthResponse signup(SignupRequest signupRequest) {
        // Check if user with the given employeeId already exists
        if (userRepository.existsByEmployeeId(signupRequest.getEmployeeId())) {
            throw new RuntimeException("Employee ID already taken");
        }

        // Encode password before saving
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        // Create a new UserModel
        UserModel user = new UserModel();
        user.setEmployeeId(signupRequest.getEmployeeId());
        user.setName(signupRequest.getName());
        user.setPassword(encodedPassword);
        user.setRole(signupRequest.getRole());

        // Save the new user to the database
        UserModel createdUser = userRepository.save(user);

        // Generate JWT token after saving the user
        String token = jwtProvider.generateToken(createdUser);

        // Return response with token, role, and name
        return new AuthResponse(token, createdUser.getRole(), createdUser.getName());
    }
}

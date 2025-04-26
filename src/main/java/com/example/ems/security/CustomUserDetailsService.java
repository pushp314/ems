package com.example.ems.security;

import com.example.ems.models.UserModel;
import com.example.ems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String employeeId) throws UsernameNotFoundException {
        // Retrieves the user by employeeId, or throws an exception if not found
        UserModel user = userRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with employeeId: " + employeeId));
        
        // Return the user as UserDetails (which is UserModel in this case)
        return user;
    }
}

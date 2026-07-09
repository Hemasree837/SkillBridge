package com.skillbridge.service;

import com.skillbridge.dto.LoginRequest;
import com.skillbridge.dto.RegisterRequest;
import com.skillbridge.dto.UserResponse;
import com.skillbridge.entity.User;
import com.skillbridge.exception.BadRequestException;
import com.skillbridge.exception.ResourceNotFoundException;
import com.skillbridge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Contains all business logic related to Users: registration, login,
 * viewing and updating profiles.
 *
 * NOTE (v1): Passwords are stored and compared as plain text since this
 * version does not use Spring Security / JWT, as requested. In a real
 * production app you should always hash passwords (e.g. with BCrypt).
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("An account with this email already exists");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setCollege(request.getCollege());
        user.setDepartment(request.getDepartment());
        user.setYear(request.getYear());
        user.setBio(request.getBio());

        User saved = userRepository.save(user);
        return UserResponse.fromEntity(saved);
    }

    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        return UserResponse.fromEntity(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::fromEntity)
                .toList();
    }

    public UserResponse getUserById(Long id) {
        User user = findUserOrThrow(id);
        return UserResponse.fromEntity(user);
    }

    public UserResponse updateUser(Long id, RegisterRequest request) {
        User user = findUserOrThrow(id);

        user.setFullName(request.getFullName());
        user.setCollege(request.getCollege());
        user.setDepartment(request.getDepartment());
        user.setYear(request.getYear());
        user.setBio(request.getBio());

        // Only update password if a new one was actually provided
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(request.getPassword());
        }

        User updated = userRepository.save(user);
        return UserResponse.fromEntity(updated);
    }

    /**
     * Searches users by skill name, college, or department.
     * Any of the three parameters can be null/empty to skip that filter.
     */
    public List<UserResponse> searchUsers(String skillName, String college, String department) {
        return userRepository.findAll().stream()
                .filter(user -> skillName == null || skillName.isBlank() ||
                        user.getSkills().stream().anyMatch(s ->
                                s.getSkillName().toLowerCase().contains(skillName.toLowerCase())))
                .filter(user -> college == null || college.isBlank() ||
                        (user.getCollege() != null && user.getCollege().toLowerCase().contains(college.toLowerCase())))
                .filter(user -> department == null || department.isBlank() ||
                        (user.getDepartment() != null && user.getDepartment().toLowerCase().contains(department.toLowerCase())))
                .map(UserResponse::fromEntity)
                .toList();
    }

    // Helper used internally and by other services (e.g. SkillService, SwapRequestService)
    public User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}

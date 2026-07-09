package com.skillbridge.controller;

import com.skillbridge.dto.RegisterRequest;
import com.skillbridge.dto.UserResponse;
import com.skillbridge.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles viewing, updating, and searching user profiles.
 * Base path: /api/users
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    // Search users by skill name, college, or department
    // Example: /api/users/search?skillName=React&college=MTIET&department=CSE
    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> searchUsers(
            @RequestParam(required = false) String skillName,
            @RequestParam(required = false) String college,
            @RequestParam(required = false) String department) {
        return ResponseEntity.ok(userService.searchUsers(skillName, college, department));
    }
}

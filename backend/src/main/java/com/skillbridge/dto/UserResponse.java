package com.skillbridge.dto;

import com.skillbridge.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Safe representation of a User that is sent back to the frontend.
 * Deliberately excludes the password field.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private String college;
    private String department;
    private Integer year;
    private String bio;

    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getCollege(),
                user.getDepartment(),
                user.getYear(),
                user.getBio()
        );
    }
}

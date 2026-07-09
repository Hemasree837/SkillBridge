package com.skillbridge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data received from the frontend when adding or editing a skill.
 */
@Data
public class SkillRequest {

    @NotBlank(message = "Skill name is required")
    private String skillName;

    private String level;

    @NotBlank(message = "Type is required (TEACH or LEARN)")
    private String type;

    @NotNull(message = "User id is required")
    private Long userId;
}

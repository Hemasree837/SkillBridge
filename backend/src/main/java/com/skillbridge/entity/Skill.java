package com.skillbridge.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a single skill entry belonging to a user.
 * type = TEACH  -> the user can teach this skill to others
 * type = LEARN  -> the user wants to learn this skill from others
 */
@Entity
@Table(name = "skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Skill name is required")
    @Column(nullable = false)
    private String skillName;

    // e.g. Beginner, Intermediate, Advanced
    private String level;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SkillType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    public enum SkillType {
        TEACH,
        LEARN
    }
}

package com.skillbridge.controller;

import com.skillbridge.dto.SkillRequest;
import com.skillbridge.entity.Skill;
import com.skillbridge.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles adding, editing, deleting, and viewing skills.
 * Base path: /api/skills
 */
@RestController
@RequestMapping("/api/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @GetMapping
    public ResponseEntity<List<Skill>> getAllSkills(@RequestParam(required = false) Long userId) {
        if (userId != null) {
            return ResponseEntity.ok(skillService.getSkillsByUser(userId));
        }
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    @PostMapping
    public ResponseEntity<Skill> addSkill(@Valid @RequestBody SkillRequest request) {
        Skill skill = skillService.addSkill(request);
        return new ResponseEntity<>(skill, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @Valid @RequestBody SkillRequest request) {
        return ResponseEntity.ok(skillService.updateSkill(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }
}

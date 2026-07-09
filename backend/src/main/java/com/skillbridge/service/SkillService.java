package com.skillbridge.service;

import com.skillbridge.dto.SkillRequest;
import com.skillbridge.entity.Skill;
import com.skillbridge.entity.User;
import com.skillbridge.exception.BadRequestException;
import com.skillbridge.exception.ResourceNotFoundException;
import com.skillbridge.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Contains all business logic for managing a user's skills
 * (skills they can teach, and skills they want to learn).
 */
@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UserService userService;

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public List<Skill> getSkillsByUser(Long userId) {
        return skillRepository.findByUserId(userId);
    }

    public Skill addSkill(SkillRequest request) {
        User user = userService.findUserOrThrow(request.getUserId());

        Skill skill = new Skill();
        skill.setSkillName(request.getSkillName());
        skill.setLevel(request.getLevel());
        skill.setType(parseType(request.getType()));
        skill.setUser(user);

        return skillRepository.save(skill);
    }

    public Skill updateSkill(Long skillId, SkillRequest request) {
        Skill skill = findSkillOrThrow(skillId);

        skill.setSkillName(request.getSkillName());
        skill.setLevel(request.getLevel());
        skill.setType(parseType(request.getType()));

        return skillRepository.save(skill);
    }

    public void deleteSkill(Long skillId) {
        Skill skill = findSkillOrThrow(skillId);
        skillRepository.delete(skill);
    }

    public List<Skill> searchBySkillName(String skillName) {
        return skillRepository.findBySkillNameContainingIgnoreCase(skillName);
    }

    private Skill findSkillOrThrow(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + id));
    }

    private Skill.SkillType parseType(String type) {
        try {
            return Skill.SkillType.valueOf(type.toUpperCase());
        } catch (Exception e) {
            throw new BadRequestException("Skill type must be either TEACH or LEARN");
        }
    }
}

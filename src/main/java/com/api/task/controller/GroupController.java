package com.api.task.controller;

import com.api.task.exception.ResourceNotFoundException;
import com.api.task.model.Group;
import com.api.task.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    GroupRepository groupRepository;

    //Get ALL
    @GetMapping("")
    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    //Create
    @PostMapping("")
    public Group create(@Valid @RequestBody Group group) {
        group.setGroup_id(null);
        return groupRepository.save(group);
    }

    //Get Single by id
    @GetMapping("/{id}")
    public Group getById(@PathVariable(value = "id") Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group", "id", groupId));
    }


    //Update
    @PutMapping("/{id}")
    public Group update(@PathVariable(value = "id") Long groupId, @Valid @RequestBody Group groupDetails) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group", "id", groupId));

        group.setName(groupDetails.getName());
        group.setToken(groupDetails.getToken());
        return groupRepository.save(group);
    }

    //Delete
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group", "id", groupId));

        groupRepository.delete(group);

        return ResponseEntity.ok().build();
    }
}

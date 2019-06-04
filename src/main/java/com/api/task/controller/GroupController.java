package com.api.task.controller;

import com.api.task.exception.ResourceNotFoundException;
import com.api.task.model.Group;
import com.api.task.model.Token;
import com.api.task.model.User;
import com.api.task.repository.GroupRepository;
import com.api.task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    //Get ALL
    @GetMapping("")
    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    //Create
    @PostMapping("")
    public Group create(@Valid @RequestBody Group group) {
        // Add owner to group
        User user = userRepository.findById(group.getOwner().getUser_id()).orElseThrow(() -> new ResourceNotFoundException("Owner", "id", group.getOwner().getUser_id()));
        group.getUsers().add(user);

        return groupRepository.save(group);
    }

    //Add user
    @PostMapping("/token")
    public Group addUser(@Valid @RequestBody Token tokenRequest) {
        Group group = groupRepository.findByToken(tokenRequest.getToken()).orElseThrow(() -> new ResourceNotFoundException("Group", "token", tokenRequest.getToken()));
        User user = userRepository.findById(tokenRequest.getUser().getUser_id()).orElseThrow(() -> new ResourceNotFoundException("User", "id", tokenRequest.getUser().getUser_id()));

        group.setOwner(user);
        group.getUsers().add(user);

        return groupRepository.save(group);
    }

    //Get all useres
    @GetMapping("/{id}/users")
    public Set<User> getUsersById(@PathVariable(value = "id") Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group", "id", groupId)).getUsers();
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

    //Delete
    @DeleteMapping("{id}/users/{id_user}")
    public Group deleteUser(@PathVariable(value = "id") Long groupId, @PathVariable(value = "id_user") Long userId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group", "id", groupId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("USer", "id", userId));
        group.getUsers().remove(user);

        return groupRepository.save(group);
    }

}

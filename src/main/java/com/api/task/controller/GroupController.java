package com.api.task.controller;

import com.api.task.model.Group;
import com.api.task.respository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

//    //Create
    @PostMapping("")
    public Group create(@Valid @RequestBody Group group) {
        group.setGroup_id(null);
        return groupRepository.save(group);
    }
//
//    //Get Single by id
//    @GetMapping("/{id}")
//    public User getById(@PathVariable(value = "id") Long userId) {
//        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
//    }
//
//    //Update
//    @PutMapping("/{id}")
//    public User update(@PathVariable(value = "id") Long userId, @Valid @RequestBody User userDetails) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
//
////        if (userDetails.getPassword() == null || userDetails.getPassword().equals("")) {
////            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
////        }
//
//
//        return userRepository.save(user);
//    }
//
//    //Delete
//    @DeleteMapping("{id}")
//    public ResponseEntity<?> delete(@PathVariable(value = "id") Long userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
//
//        userRepository.delete(user);
//
//        return ResponseEntity.ok().build();
//    }
}

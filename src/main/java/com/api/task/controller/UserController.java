package com.api.task.controller;

import com.api.task.exception.ResourceNotFoundException;
import com.api.task.model.Task;
import com.api.task.model.User;
import com.api.task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    //Get ALL
    @GetMapping("")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    //Create
    @PostMapping("")
    public User create(@Valid @RequestBody User user) {
        // Encrypting password
//        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    //Get Single by id
    @GetMapping("/{id}")
    public User getById(@PathVariable(value = "id") Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    //Get Tasks for user
    @GetMapping("/{id}/tasks")
    public Set<Task> getTasksById(@PathVariable(value = "id") Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return user.getTasks();
    }

    //Update
    @PutMapping("/{id}")
    public User update(@PathVariable(value = "id") Long userId, @Valid @RequestBody User userDetails) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

//        if (userDetails.getPassword() == null || userDetails.getPassword().equals("")) {
//            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
//        }


        return userRepository.save(user);
    }

    //Delete
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }
}

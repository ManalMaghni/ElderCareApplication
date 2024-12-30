package com.example.elderhealth.controllers;

import com.example.elderhealth.entities.Medicine;
import com.example.elderhealth.entities.User;
import com.example.elderhealth.services.MedicineService;
import com.example.elderhealth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final MedicineService medicineService;



    @Autowired
    public UserController(UserService userService, MedicineService medicineService) {
        this.userService = userService;
        this.medicineService = medicineService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }



    @GetMapping("/{userId}/medicines")
    public ResponseEntity<List<Medicine>> getMedicinesByUserId(@PathVariable Long userId) {
        List<Medicine> medicines = medicineService.getMedicinesByUser(userId);
        if (medicines.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(medicines);
    }
    @GetMapping("/login/{login}")
    public ResponseEntity<User> getUserByLogin(@PathVariable String login) {
        User user = userService.findByLogin(login);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}

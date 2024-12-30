package com.example.elderhealth.services;

import org.springframework.stereotype.Service;
import java.util.List;

import com.example.elderhealth.entities.User;
import com.example.elderhealth.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);

        if (user.getNom() != null) {
            existingUser.setNom(user.getNom());
        }
        if (user.getTelephone() != null) {
            existingUser.setTelephone(user.getTelephone());
        }
        if (user.getLogin() != null) {
            existingUser.setLogin(user.getLogin());
        }
        if (user.getPassword() != null) {
            existingUser.setPassword(user.getPassword());
        }
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

}

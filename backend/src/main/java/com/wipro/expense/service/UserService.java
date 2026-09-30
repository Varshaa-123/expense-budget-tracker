package com.wipro.expense.service;

import com.wipro.expense.entity.User;
import com.wipro.expense.exception.ResourceNotFoundException;
import com.wipro.expense.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {   // constructor injection
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("A user with email " + user.getEmail() + " already exists");
        }
        user.setUserId(null);   // make sure it is a NEW row
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    public User updateUser(Long id, User newData) {
        User user = getUserById(id);
        user.setUserName(newData.getUserName());
        user.setEmail(newData.getEmail());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.delete(getUserById(id));
    }
}

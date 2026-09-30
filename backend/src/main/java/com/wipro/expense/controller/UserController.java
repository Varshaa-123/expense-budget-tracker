package com.wipro.expense.controller;

import com.wipro.expense.entity.User;
import com.wipro.expense.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/** Controllers only receive requests and call the service. No SQL, no business logic. */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")   // allow the React app to call this API
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)           // 201
    public User create(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @Valid @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)        // 204
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}

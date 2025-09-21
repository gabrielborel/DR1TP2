package infnet.edu.br.DR1TP2.controllers;

import infnet.edu.br.DR1TP2.dtos.CreateUserDTO;
import infnet.edu.br.DR1TP2.dtos.UpdateUserDTO;
import infnet.edu.br.DR1TP2.dtos.UserResponseDTO;
import infnet.edu.br.DR1TP2.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        try {
            var user = userService.createUser(createUserDTO);
            return ResponseEntity.ok(new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail()));
        } catch(RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        var users = userService.getAllUsers().stream()
                .map(u -> new UserResponseDTO(u.getId(), u.getUsername(), u.getEmail()))
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id)
                .map(u -> ResponseEntity.ok(new UserResponseDTO(u.getId(), u.getUsername(), u.getEmail())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {
        try {
            return userService.updateUser(updateUserDTO)
                    .map(u -> ResponseEntity.ok(new UserResponseDTO(u.getId(), u.getUsername(), u.getEmail())))
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

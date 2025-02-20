package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FirstRESTController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public FirstRESTController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    private UserDTO convertToUserDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        return userDTO;
    }

    private User convertToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setRoles(userDTO.getRoles());
        user.setEmail(userDTO.getEmail());
        user.setAge((byte) userDTO.getAge());
        return user;
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> userPage(Principal principal) {
        Optional<User> optionalUser = Optional.ofNullable(userService.findByFirstname(principal.getName()));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserDTO userDTO = convertToUserDTO(user);
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUserList() {
        List<UserDTO> users = userService.getAllUsers().stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user != null) {
            UserDTO userDTO = convertToUserDTO(user);
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/roles")
    public ResponseEntity<List<Role>> allRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }

    @PostMapping("/admin")
    public ResponseEntity<Void> saveUser(@RequestBody UserDTO userDTO) {
        userService.save(null, convertToUser(userDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/admin/{id}")
    public ResponseEntity<Void> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        userService.save(userService.getById(id), convertToUser(userDTO));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
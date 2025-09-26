package com.backend.backend.controller;

import com.backend.backend.dto.PageDTO;
import com.backend.backend.dto.UserRequestDTO;
import com.backend.backend.dto.UserResponseDTO;
import com.backend.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> saveNewUser(@RequestBody @Valid UserRequestDTO dto){
        return ResponseEntity.ok().body(this.userService.save(dto));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserRequestDTO dto, @PathVariable UUID userId){
        this.userService.update(dto, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PageDTO<UserResponseDTO>> getAllUsers(Pageable pageable,
                                                             @RequestParam(required = false) String searchTerm){
        Page<UserResponseDTO> page = this.userService.getAll(pageable, searchTerm);
        return ResponseEntity.ok(new PageDTO<>(page));

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID userId){
        this.userService.delete(userId);
        return ResponseEntity.ok().build();
    }

}

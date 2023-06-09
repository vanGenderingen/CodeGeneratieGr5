package io.swagger.api;

import io.swagger.model.DTO.*;
import io.swagger.model.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Validated
@RequestMapping("/users")
@SecurityRequirement(name = "JWTAuth")
public interface UsersApi {

    @PostMapping
    ResponseEntity<User> createUser(@Valid @RequestBody CreateUserDTO createUserDTO);

    @GetMapping
    ResponseEntity<List<GetUserDTO>> getUsers(
            @Valid @RequestParam(value = "limit", defaultValue = "10") @Min(0) @Max(50) Integer limit,
            @Valid @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @Valid @RequestParam(value = "searchstrings", required = false) String searchStrings,
            @Valid @RequestParam(value = "Email", required = false) String Email
    );

    @GetMapping("/{userID}")
    ResponseEntity<GetUserDTO> getUserById(
            @Valid @PathVariable("userID") UUID userID,
            Principal principal
    );
    @PutMapping("/{userID}")
    ResponseEntity<GetUserDTO> updateUser(
            @Valid @PathVariable("userID") UUID userID,
            @Valid @RequestBody UpdateUserDTO updateUserDTO,
            Principal principal
    );

}
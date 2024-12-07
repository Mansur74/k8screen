package io.k8screen.backend.controller;

import io.k8screen.backend.data.user.UserForm;
import io.k8screen.backend.data.user.UserItem;
import io.k8screen.backend.data.user.UserLoginRequest;
import io.k8screen.backend.service.AuthService;
import io.k8screen.backend.service.UserService;
import io.k8screen.backend.util.JwtUtil;
import jakarta.validation.Valid;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final @NotNull AuthService authService;

  public AuthController(
      final @NotNull JwtUtil jwtUtil,
      final @NotNull AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login(
      @Valid @RequestBody final UserLoginRequest loginRequest) {
    return ResponseEntity.status(HttpStatus.OK).body( this.authService.login(loginRequest));
  }

  @PostMapping("/register")
  public ResponseEntity<Map<String, String>> register(@Valid @RequestBody final UserForm userForm) {
    return ResponseEntity.status(HttpStatus.CREATED).body( this.authService.register(userForm));
  }
}
